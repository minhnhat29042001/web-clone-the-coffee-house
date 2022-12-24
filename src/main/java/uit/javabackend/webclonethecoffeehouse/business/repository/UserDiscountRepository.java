package uit.javabackend.webclonethecoffeehouse.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.business.model.UserDiscount;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDiscountRepository extends JpaRepository<UserDiscount, UUID> {

    Optional<UserDiscount> findByUserUsername(String name);

    Optional<UserDiscount> findByDiscount_Code(String code);

    Optional<UserDiscount> findByDescription(String description);

    @Query("SELECT ud FROM UserDiscount ud where ud.user.id = ?1 and ud.discount.id = ?2 ")
    Optional<UserDiscount> findByUserIdAndDiscountId(UUID userId, UUID discountId);

}
