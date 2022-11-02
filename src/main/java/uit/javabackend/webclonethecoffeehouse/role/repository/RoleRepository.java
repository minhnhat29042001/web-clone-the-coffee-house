package uit.javabackend.webclonethecoffeehouse.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.role.model.Role;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByCode(String code);

    void deleteByCode(String code);

    Optional<Role> findByName(String name);

    @Query("select (count(r) > 0) from Role r where r.id = ?1")
    boolean existsById(UUID id);
}
