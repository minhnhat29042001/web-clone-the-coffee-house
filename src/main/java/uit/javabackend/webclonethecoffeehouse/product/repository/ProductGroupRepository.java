package uit.javabackend.webclonethecoffeehouse.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.model.ProductGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup, UUID> {
    Optional<ProductGroup> findByName(String name);

    void deleteByName(String name);

    @Query("SELECT pg FROM ProductGroup pg WHERE " + "pg.name LIKE CONCAT ('%',:query,'%')")
    List<ProductGroup> searchProductGroup(String query);
}
