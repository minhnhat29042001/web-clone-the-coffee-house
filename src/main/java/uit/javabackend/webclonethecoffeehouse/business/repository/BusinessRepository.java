package uit.javabackend.webclonethecoffeehouse.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.business.model.Business;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Business, UUID> {

    Optional<Business> findByCompanyName(String name);
}
