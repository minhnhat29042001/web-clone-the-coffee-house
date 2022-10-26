package uit.javabackend.webclonethecoffeehouse.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.currency.model.Currency;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
    Optional<Currency> findByName(String name);

    void deleteByName(String name);
}
