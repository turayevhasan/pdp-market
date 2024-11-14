package uz.pdp.order_service.payload.req;

import lombok.Data;
import uz.pdp.order_service.payload.dto.OrderLinesDto;

import java.util.List;

@Data
public class OrderAddReq {
    List<OrderLinesDto> orderLines;
}
