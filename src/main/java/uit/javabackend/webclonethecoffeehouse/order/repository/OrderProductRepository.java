package uit.javabackend.webclonethecoffeehouse.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.order.model.OrderProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
    Optional<OrderProduct> findById(UUID id);

    List<OrderProduct> findAllByOrderId (UUID orderId);
    List<OrderProduct> findAllByProductId (UUID productId);
}
