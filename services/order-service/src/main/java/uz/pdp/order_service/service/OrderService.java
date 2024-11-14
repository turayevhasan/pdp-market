package uz.pdp.order_service.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import uz.pdp.order_service.conf.CustomerFeignClients;
import uz.pdp.order_service.conf.PaymentFeignClients;
import uz.pdp.order_service.conf.ProductFeignClients;
import uz.pdp.order_service.entity.Order;
import uz.pdp.order_service.entity.OrderLine;
import uz.pdp.order_service.enums.OrderStatus;
import uz.pdp.order_service.enums.ErrorTypeEnum;
import uz.pdp.order_service.exceptions.RestException;
import uz.pdp.order_service.mapper.OrderMapper;
import uz.pdp.order_service.payload.dto.OrderLinesDto;
import uz.pdp.order_service.payload.dto.ProductDto;
import uz.pdp.order_service.payload.req.OrderAddReq;
import uz.pdp.order_service.payload.req.OrderUpdateReq;
import uz.pdp.order_service.payload.req.ProductUpdateReq;
import uz.pdp.order_service.payload.res.DataFromRes;
import uz.pdp.order_service.payload.res.OrderRes;
import uz.pdp.order_service.repository.OrderLineRepository;
import uz.pdp.order_service.repository.OrderRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderConfirmation orderConfirmation;
    private final ProductFeignClients productFeignClients;
    private final CustomerFeignClients customerFeignClients;

    public OrderRes add(OrderAddReq req) {
        List<OrderLinesDto> linesDto = req.getOrderLines();
        List<ProductUpdateReq> requests = new ArrayList<>();

        for (OrderLinesDto line : linesDto) {
            DataFromRes<ProductDto> data = productFeignClients.getProductById(line.getProductId());

            if (!data.getSuccess()) {
                throw RestException.restThrow(ErrorTypeEnum.PRODUCT_NOT_FOUND);
            } else if (data.getData().getAvailableQuantity() - line.getQuantity() < 0) {
                throw RestException.restThrow(ErrorTypeEnum.PRODUCT_QUANTITY_UN_AVAILABLE);
            }
            requests.add(new ProductUpdateReq(line.getProductId(), data.getData().getAvailableQuantity() - line.getQuantity()));
        }

        for (ProductUpdateReq request : requests) {
            productFeignClients.updateProduct(request); //updating requests for product available quantity after adding order
        }

        Order order = new Order();
        order.setCustomerId("671637c4ec57993b8bd5eee8");
        orderRepository.save(order);

        List<OrderLine> lines = new ArrayList<>();
        for (OrderLinesDto dto : linesDto) {
            lines.add(OrderLine.builder()
                    .productId(dto.getProductId())
                    .quantity(dto.getQuantity())
                    .order(order)
                    .build()
            );
        }
        orderLineRepository.saveAll(lines);

        order.setOrderLines(lines);

        return OrderMapper.entityToDto(order);
    }

    public OrderRes update(OrderUpdateReq req) {
        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.ORDER_NOT_FOUND));

        if (order.getStatus().equals(OrderStatus.ARCHIVED)) {
            throw RestException.restThrow(ErrorTypeEnum.ORDER_ALREADY_CONFIRMED);
        }
        if (req.getStatus().equals(OrderStatus.ARCHIVED)) {
            throw RestException.restThrow(ErrorTypeEnum.CANNOT_CHANGE_THIS_STATUS);
        }

        if (order.getStatus().equals(OrderStatus.IN_PROGRESS) || order.getStatus().equals(OrderStatus.DECLINED)) {
            order.setStatus(getIfExists(req.getStatus(), order.getStatus()));
            orderRepository.save(order);
        }

        if (order.getStatus().equals(OrderStatus.CONFIRMED)) {
            orderConfirmation.sendOrderConfirmation(order, true); //send order_confirmation to notification service;
            order.setStatus(OrderStatus.ARCHIVED);  //archived after order_confirmation
            orderRepository.save(order);
        } else if (order.getStatus().equals(OrderStatus.DECLINED)) {
            orderConfirmation.sendOrderConfirmation(order, false);
        }

        return OrderMapper.entityToDto(order);
    }

    public OrderRes getOne(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.ORDER_NOT_FOUND));

        return OrderMapper.entityToDto(order);
    }

    private <E> E getIfExists(E newObj, E oldObj) {
        return Objects.nonNull(newObj) ? newObj : oldObj;
    }

    public List<OrderRes> getAllByCustomer(String id) {
        if (!customerFeignClients.getCustomerById(id).getSuccess()) {
            throw RestException.restThrow(ErrorTypeEnum.CUSTOMER_NOT_FOUND);
        }

        List<Order> orders = orderRepository.findAllByCustomerId(id);

        if (orders.isEmpty()) {
            throw RestException.restThrow(ErrorTypeEnum.ORDER_NOT_FOUND);
        }

        return OrderMapper.entityToList(orders);
    }
}
