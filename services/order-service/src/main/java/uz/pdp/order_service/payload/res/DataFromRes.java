package uz.pdp.order_service.payload.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataFromRes<T> {
    private Boolean success;

    private String message;

    private T data;
}
