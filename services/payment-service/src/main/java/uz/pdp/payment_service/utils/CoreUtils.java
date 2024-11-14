package uz.pdp.payment_service.utils;

import java.util.Objects;

public class CoreUtils {
    public static  <E> E getIfExists(E newObj, E oldObj) {
        return Objects.nonNull(newObj) ? newObj : oldObj;
    }
}
