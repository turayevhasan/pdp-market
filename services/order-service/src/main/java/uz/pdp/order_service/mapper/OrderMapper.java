package uz.pdp.order_service.mapper;

import uz.pdp.order_service.entity.Order;
import uz.pdp.order_service.entity.OrderLine;
import uz.pdp.order_service.payload.dto.OrderLinesDto;
import uz.pdp.order_service.payload.res.OrderRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface OrderMapper {
    static OrderRes entityToDto(Order order) {
        return OrderRes.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .status(order.getStatus().name())
                .orderLines(linesToDto(order.getOrderLines()))
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    static List<OrderRes> entityToList(List<Order> orders) {
        List<OrderRes> res = new ArrayList<>();
        orders.forEach(order -> res.add(entityToDto(order)));
        return res;
    }

    static List<OrderLinesDto> linesToDto(List<OrderLine> lines){
        List<OrderLinesDto> res = new ArrayList<>();
        lines.forEach(line -> res.add(new OrderLinesDto(line.getProductId(), line.getQuantity())));
        return res;
    }


}
