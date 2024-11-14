package uz.pdp.customer_service.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerAddReq {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;

    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    private String street;
    @NotBlank
    private String houseNumber;
    @NotBlank
    private String zipCode;
}
