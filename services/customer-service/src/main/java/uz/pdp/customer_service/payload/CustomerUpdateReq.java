package uz.pdp.customer_service.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerUpdateReq {
    @NotBlank
    private String email;
    private String firstName;
    private String lastName;
    private String street;
    private String houseNumber;
    private String zipCode;
}
