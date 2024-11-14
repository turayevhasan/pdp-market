package uz.pdp.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.product_service.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    void deleteAllByCategoryId(Long categoryId);
    List<Product> findAllByCategoryId(Long id);
    boolean existsByName(String name);
}
