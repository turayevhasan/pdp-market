package uz.pdp.order_service.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorTypeEnum {
    TOKEN_NOT_VALID,
    USER_PERMISSION_RESTRICTION(HttpStatus.UNAUTHORIZED),
    FORBIDDEN(HttpStatus.FORBIDDEN),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED),
    WRONG_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_NOT_EXPIRED(HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND_OR_DISABLED(HttpStatus.FORBIDDEN),
    LOGIN_OR_PASSWORD_ERROR(HttpStatus.FORBIDDEN),
    EMAIL_ALREADY_EXISTS,
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND),
    FILE_CANNOT_DELETED,
    ERROR_SAVING_FILE,
    ATTACHMENT_NOT_FOUND(HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND),
    PRODUCT_QUANTITY_UN_AVAILABLE,
    ORDER_ALREADY_CONFIRMED, CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND),
    CANNOT_CHANGE_THIS_STATUS();

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    ErrorTypeEnum(HttpStatus status) {
        this.status = status;
    }

    ErrorTypeEnum() {
    }
}
