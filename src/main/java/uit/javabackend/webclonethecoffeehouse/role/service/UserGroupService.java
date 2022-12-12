package uit.javabackend.webclonethecoffeehouse.role.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.role.dto.UserGroupDTO;
import uit.javabackend.webclonethecoffeehouse.role.dto.UserGroupWithUsersDTO;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;
import uit.javabackend.webclonethecoffeehouse.role.repository.UserGroupRepository;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserGroupService extends GenericService<UserGroup, UserGroupDTO, UUID> {
    UserGroupWithUsersDTO addUsers(UUID userGroupId, List<UUID> ids);

    List<UserGroupWithUsersDTO> findAllDtoIncludeUsers();

    List<UserGroup> findByRoleId(UUID roleId);

    void deleteByName(String name);

    public UserGroupDTO update(UserGroupDTO userGroupDTO);

    UserGroupWithUsersDTO removeUsers(UUID userGroupId, List<UUID> ids);

    UserGroupDTO findUserGroupByNameDTO(String name);

    UserGroup findUserGroupByName(String name);
}

@Service
@Transactional
class UserGroupServiceImpl implements UserGroupService {
    private final UserGroupRepository repository;
    private final UserRepository userRepository;
    private final TCHMapper tchMapper;


    UserGroupServiceImpl(UserGroupRepository repository, UserRepository userRepository, TCHMapper tchMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.tchMapper = tchMapper;
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

        ids.forEach(userId -> {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                userGroup.addUser(user);
                user.getUserGroups().add(userGroup);
            }
        });
        return tchMapper.map(userGroup, UserGroupWithUsersDTO.class);
    }

    @Override
    public List<UserGroupWithUsersDTO> findAllDtoIncludeUsers() {
        return repository.findAllWithUsers()
                .stream()
                .map(model -> tchMapper.map(model, UserGroupWithUsersDTO.class))
                .toList();
    }

    public UserGroupDTO update(UserGroupDTO userGroupDTO) {
        UserGroup userGroup = tchMapper.map(userGroupDTO, UserGroup.class);
        return tchMapper.map(repository.save(userGroup), UserGroupDTO.class);
    }

    @Override
    public UserGroupWithUsersDTO removeUsers(UUID userGroupId, List<UUID> ids) {
        UserGroup userGroup = repository.findById(userGroupId)
                .orElseThrow(() -> new ValidationException("UserGroup is not existed."));

        ids.forEach(userId -> {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                userGroup.removeUser(user);
                user.getUserGroups().remove(userGroup);
            }
        });
        return tchMapper.map(userGroup, UserGroupWithUsersDTO.class);
    }

    @Override
    public UserGroupDTO findUserGroupByNameDTO(String name) {
        Optional<UserGroup> userGroup = repository.findByName(name);
        return tchMapper.map(userGroup, UserGroupDTO.class);
    }

    @Override
    public UserGroup findUserGroupByName(String name) {
        Optional<UserGroup> userGroup = repository.findByName(name);
        return userGroup.orElse(null);
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
