package uz.pdp.payment_service.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.payment_service.entity.Payment;
import uz.pdp.payment_service.payload.req.ReqCreatePayment;
import uz.pdp.payment_service.payload.res.ResPayment;

import java.util.ArrayList;
import java.util.List;

public interface PaymentMapper {
    static ResPayment fromEntityToDto(Payment payment) {
        return ResPayment.builder()
                .id(payment.getId())
                .customerId(payment.getCustomerId())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }

    static Payment fromDtoToEntity(ReqCreatePayment req) {
        return Payment.builder()
                .customerId(req.getCustomerId())
                .orderId(req.getOrderId())
                .amount(req.getAmount())
                .build();
    }

    static List<ResPayment> pageToPaymentList(Page<Payment> payments) {
        List<ResPayment> resPayments = new ArrayList<>();
        payments.forEach(payment -> resPayments.add(fromEntityToDto(payment)));
        return resPayments;
    }

    static List<ResPayment> fromEntityToList(List<Payment> payments) {
        List<ResPayment> res = new ArrayList<>();
        payments.forEach(payment -> res.add(fromEntityToDto(payment)));
        return res;
    }
}
