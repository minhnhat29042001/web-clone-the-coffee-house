package uit.javabackend.webclonethecoffeehouse.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, UUID> {

    Optional<Discount> findByCode(String name);

    Optional<Discount> findByDescription(String description);

    void deleteByCode(String code);
}
