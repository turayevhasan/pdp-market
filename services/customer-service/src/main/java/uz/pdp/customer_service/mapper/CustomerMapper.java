package uz.pdp.customer_service.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.customer_service.model.Address;
import uz.pdp.customer_service.model.Customer;
import uz.pdp.customer_service.payload.CustomerRes;
import uz.pdp.customer_service.payload.CustomerUpdateReq;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.customer_service.utils.Utils.getIfExists;

public interface CustomerMapper {

    static CustomerRes entityToRes(Customer customer) {
        Address a = customer.getAddress();
        String address = "Street :" + a.getStreet() + ", House :" + a.getHouseNumber() + ", ZipCode :" + a.getZipCode();
        return CustomerRes.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .address(address)
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    static List<CustomerRes> pagesToDto(Page<Customer> all) {
        List<CustomerRes> res = new ArrayList<>();
        all.forEach(customer -> res.add(entityToRes(customer)));
        return res;
    }

    static void updateCustomer(Customer customer, CustomerUpdateReq req) {
        Address address = customer.getAddress();

        //update street
        address.setStreet(getIfExists(req.getStreet(), address.getStreet()));
        address.setHouseNumber(getIfExists(req.getHouseNumber(), address.getHouseNumber()));
        address.setZipCode(getIfExists(req.getZipCode(), address.getZipCode()));

        //update customer
        customer.setFirstName(getIfExists(req.getFirstName(), customer.getFirstName()));
        customer.setLastName(getIfExists(req.getLastName(), customer.getLastName()));
    }
}
