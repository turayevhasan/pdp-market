package uz.pdp.payment_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.payment_service.conf.CustomerFeignClients;
import uz.pdp.payment_service.conf.OrderFeignClients;
import uz.pdp.payment_service.entity.Payment;
import uz.pdp.payment_service.entity.PaymentStatus;
import uz.pdp.payment_service.enums.ErrorTypeEnum;
import uz.pdp.payment_service.exceptions.RestException;
import uz.pdp.payment_service.mapper.PaymentMapper;
import uz.pdp.payment_service.payload.dto.CustomerDto;
import uz.pdp.payment_service.payload.req.ReqCreatePayment;
import uz.pdp.payment_service.payload.req.ReqUpdatePayment;
import uz.pdp.payment_service.payload.res.DataFromRes;
import uz.pdp.payment_service.payload.res.ResPayment;
import uz.pdp.payment_service.repository.PaymentRepository;

import java.util.List;

import static uz.pdp.payment_service.utils.CoreUtils.getIfExists;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentConfirmation paymentConfirmation;
    private final CustomerFeignClients customerFeignClients;
    private final OrderFeignClients orderFeignClients;

    public ResPayment create(ReqCreatePayment req) {
        if (!customerFeignClients.getCustomerById(req.getCustomerId()).getSuccess()) {
            throw RestException.restThrow(ErrorTypeEnum.CUSTOMER_NOT_FOUND);
        }

        if (!orderFeignClients.getOrderById(req.getOrderId()).getSuccess()) {
            throw RestException.restThrow(ErrorTypeEnum.ORDER_NOT_FOUND);
        }

        if (paymentRepository.existsByOrderIdAndStatus(req.getOrderId(), PaymentStatus.IN_PROGRESS)) {
            throw RestException.restThrow(ErrorTypeEnum.THIS_ORDER_PAYMENT_IN_PROGRESS);
        }

        Payment payment = PaymentMapper.fromDtoToEntity(req);

        paymentRepository.save(payment);

        return PaymentMapper.fromEntityToDto(payment);
    }

    public ResPayment update(ReqUpdatePayment req) {
        DataFromRes<CustomerDto> resFromCustomer = customerFeignClients.getCustomerById(req.getCustomerId());

        if (!resFromCustomer.getSuccess()) {
            throw RestException.restThrow(ErrorTypeEnum.CUSTOMER_NOT_FOUND);
        }

        Payment payment = paymentRepository.findByCustomerIdAndStatus(req.getCustomerId(), PaymentStatus.IN_PROGRESS)
                .orElseThrow(RestException.thew(ErrorTypeEnum.ACTIVE_PAYMENT_NOT_FOUND));

        if (payment.getStatus().equals(PaymentStatus.ARCHIVED)) {
            throw RestException.restThrow(ErrorTypeEnum.PAYMENT_ALREADY_PAID);
        }

        if (payment.getStatus().equals(PaymentStatus.IN_PROGRESS) || payment.getStatus().equals(PaymentStatus.DECLINED)) {
            payment.setStatus(getIfExists(req.getStatus(), payment.getStatus()));
            paymentRepository.save(payment); //updated
        }

        if (payment.getStatus().equals(PaymentStatus.PAID)) {
            paymentConfirmation.sendPaymentConfirmation(payment, resFromCustomer.getData().getEmail());  //send confirmation to notification_service

            payment.setStatus(PaymentStatus.ARCHIVED);
            paymentRepository.save(payment); //updated
        }

        return PaymentMapper.fromEntityToDto(payment);
    }

    public List<ResPayment> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Payment> payments = paymentRepository.findAll(pageRequest);

        return PaymentMapper.pageToPaymentList(payments);
    }

    public ResPayment getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.PAYMENT_NOT_FOUND));

        return PaymentMapper.fromEntityToDto(payment);
    }

    public List<ResPayment> getAllByCustomerId(String customerId) {
        if (!customerFeignClients.getCustomerById(customerId).getSuccess()) {
            throw RestException.restThrow(ErrorTypeEnum.CUSTOMER_NOT_FOUND);
        }

        List<Payment> payments = paymentRepository.findAllByCustomerId(customerId);
        if (payments.isEmpty()) {
            throw RestException.restThrow(ErrorTypeEnum.PAYMENT_NOT_FOUND);
        }

        return PaymentMapper.fromEntityToList(payments);
    }
}
