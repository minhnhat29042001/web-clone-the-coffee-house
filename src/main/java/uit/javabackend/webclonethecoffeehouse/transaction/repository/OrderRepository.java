package uit.javabackend.webclonethecoffeehouse.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    void deleteByName(String name);

    Optional<Order> findByName(String name);
}
