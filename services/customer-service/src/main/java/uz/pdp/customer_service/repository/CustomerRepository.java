package uz.pdp.customer_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.customer_service.model.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
    Optional<Customer> findByEmail(String email);
}
