package uit.javabackend.webclonethecoffeehouse.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    void deleteByName(String name);

    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE " + "p.name LIKE CONCAT ('%',:query,'%')" + "Or p.description LIKE CONCAT ('%',:query,'%') ")
    List<Product> searchProducts(String query);
}
