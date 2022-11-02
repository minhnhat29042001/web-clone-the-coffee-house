package uit.javabackend.webclonethecoffeehouse.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UUID> {

    @Query("select ug from UserGroup ug left join fetch ug.users")
    List<UserGroup> findAllWithUsers();

    @Query("select ug from UserGroup ug left join Role r where r.id = ?1")
    List<UserGroup> findByRoleId(UUID id);

    void deleteByName(String name);

    Optional<UserGroup> findByName(String name);
}
