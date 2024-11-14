package uz.pdp.order_service.payload.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;
import uz.pdp.order_service.entity.OrderLine;
import uz.pdp.order_service.payload.dto.OrderLinesDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class OrderRes {
    private Long id;

    private String customerId;

    private String status;

    private List<OrderLinesDto> orderLines;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
