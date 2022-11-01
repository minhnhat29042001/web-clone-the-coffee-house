package uit.javabackend.webclonethecoffeehouse.role.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.role.dto.RoleDTO;
import uit.javabackend.webclonethecoffeehouse.role.dto.UserGroupDTO;
import uit.javabackend.webclonethecoffeehouse.role.dto.UserGroupWithUsersDTO;
import uit.javabackend.webclonethecoffeehouse.role.model.Role;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;
import uit.javabackend.webclonethecoffeehouse.role.repository.UserGroupRepository;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.service.UserService;

import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface UserGroupService extends GenericService<UserGroup, UserGroupDTO, UUID> {
    UserGroupWithUsersDTO addUsers(UUID userGroupId, List<UUID> ids);

    List<UserGroupWithUsersDTO> findAllDtoIncludeUsers();

    List<UserGroup> findByRoleId(UUID roleId);

    void deleteByName(String name);

    public UserGroupDTO update(UserGroupDTO userGroupDTO);
}

@Service
@Transactional
class UserGroupServiceImpl implements UserGroupService {
    private final UserGroupRepository repository;
    private final TCHMapper tchMapper;

    private final UserService userService;

    UserGroupServiceImpl(UserGroupRepository repository, TCHMapper tchMapper, UserService userService) {
        this.repository = repository;
        this.tchMapper = tchMapper;
        this.userService = userService;
    }

    @Override
    public JpaRepository<UserGroup, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getMapper() {
        return tchMapper;
    }

    @Override
    public UserGroupWithUsersDTO addUsers(UUID userGroupId, List<UUID> ids) {
        UserGroup userGroup = repository.findById(userGroupId)
                .orElseThrow(() -> new ValidationException("UserGroup is not existed."));

        List<User> users = userService.findByIds(ids);
        users.forEach(userGroup::addUser);
        return tchMapper.map(userGroup, UserGroupWithUsersDTO.class);
    }

    @Override
    public List<UserGroupWithUsersDTO> findAllDtoIncludeUsers() {
        return repository.findAllWithUsers()
                .stream()
                .map(model -> tchMapper.map(model, UserGroupWithUsersDTO.class))
                .collect(Collectors.toList());
    }

    public UserGroupDTO update(UserGroupDTO userGroupDTO) {
        UserGroup userGroup = tchMapper.map(userGroupDTO, UserGroup.class);
        return tchMapper.map(repository.save(userGroup), UserGroupDTO.class);
    }
    @Override
    public List<UserGroup> findByRoleId(UUID roleId) {
        return repository.findByRoleId(roleId);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

}
