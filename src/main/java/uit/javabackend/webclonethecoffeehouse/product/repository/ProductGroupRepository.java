package uit.javabackend.webclonethecoffeehouse.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.product.model.ProductGroup;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup, UUID> {
    Optional<ProductGroup> findByName(String name);

    void deleteByName(String name);
}
