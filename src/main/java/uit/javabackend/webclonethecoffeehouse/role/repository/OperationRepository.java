package uit.javabackend.webclonethecoffeehouse.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uit.javabackend.webclonethecoffeehouse.role.model.Operation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
    Optional<Operation> findByName(String name);

    void deleteByCode(String code);

    @Query("select o from Operation o left join Role r where r.id = ?1")
    List<Operation> findByRoleId(UUID roleId);
}
