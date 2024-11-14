package uz.pdp.customer_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.customer_service.enums.ErrorTypeEnum;
import uz.pdp.customer_service.exceptions.RestException;
import uz.pdp.customer_service.mapper.CustomerMapper;
import uz.pdp.customer_service.model.Address;
import uz.pdp.customer_service.model.Customer;
import uz.pdp.customer_service.payload.CustomerAddReq;
import uz.pdp.customer_service.payload.CustomerRes;
import uz.pdp.customer_service.payload.CustomerUpdateReq;
import uz.pdp.customer_service.payload.LoginReq;
import uz.pdp.customer_service.repository.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerRes signUp(CustomerAddReq req) {
        if (customerRepository.existsByEmail(req.getEmail())) {
            throw RestException.restThrow(ErrorTypeEnum.EMAIL_ALREADY_EXISTS);
        }
        Customer customer = Customer.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .address(new Address(req.getStreet(), req.getHouseNumber(), req.getZipCode()))
                .build();

        customerRepository.save(customer);

        return CustomerMapper.entityToRes(customer);
    }


    public String delete(String email) {
        if (!customerRepository.existsByEmail(email)) {
            throw RestException.restThrow(ErrorTypeEnum.CUSTOMER_NOT_FOUND);
        }

        customerRepository.deleteByEmail(email);
        return "Customer has been deleted";
    }

    public CustomerRes getOne(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CUSTOMER_NOT_FOUND));

        return CustomerMapper.entityToRes(customer);
    }

    public CustomerRes update(CustomerUpdateReq req) {
        Customer customer = customerRepository.findByEmail(req.getEmail())
                .orElseThrow(RestException.thew(ErrorTypeEnum.CUSTOMER_NOT_FOUND));

        CustomerMapper.updateCustomer(customer, req);
        customerRepository.save(customer);

        return CustomerMapper.entityToRes(customer);
    }

    public List<CustomerRes> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return CustomerMapper.pagesToDto(customerRepository.findAll(pageRequest));
    }

    public CustomerRes signIn(LoginReq req) {
        Customer customer = customerRepository.findByEmail(req.getEmail())
                .orElseThrow(RestException.thew(ErrorTypeEnum.CUSTOMER_NOT_FOUND));

        if (!req.getPassword().equals(customer.getPassword())) {
            throw RestException.restThrow(ErrorTypeEnum.LOGIN_OR_PASSWORD_ERROR);
        }
        return CustomerMapper.entityToRes(customer);
    }
}
