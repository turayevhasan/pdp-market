package uz.pdp.customer_service.utils;

import java.util.Objects;

public class Utils {
    public static  <E> E getIfExists(E newObj, E oldObj) {
        return Objects.nonNull(newObj) ? newObj : oldObj;
    }
}
