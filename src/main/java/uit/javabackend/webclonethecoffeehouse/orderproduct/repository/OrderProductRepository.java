package uit.javabackend.webclonethecoffeehouse.orderproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.orderproduct.model.OrderProduct;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
    Optional<OrderProduct> findByName(String name);
}
