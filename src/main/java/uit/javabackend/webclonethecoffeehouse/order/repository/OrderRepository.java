package uit.javabackend.webclonethecoffeehouse.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    void deleteByCustomerName(String name);

    Optional<Order> findByCustomerName(String name);
    Optional<Order> findById(UUID id);

    @Query("select od from Order od left join fetch od.orderProducts")
    List<Order> findAllWithOrderProducts();

}
