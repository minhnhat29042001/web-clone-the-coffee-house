package uit.javabackend.webclonethecoffeehouse.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.security.model.EmailAndJWT;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailAndJWTRepository extends JpaRepository<EmailAndJWT, UUID> {

    @Query("select ejwt from EmailAndJWT ejwt where ejwt.email=?1 and  ejwt.jwt=?2 ")
    Optional<EmailAndJWT> getEntityByEmailAndJwt(String email, String jwt);

    void deleteByEmail(String email);



}
