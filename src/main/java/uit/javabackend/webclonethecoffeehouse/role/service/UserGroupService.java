package uit.javabackend.webclonethecoffeehouse.role.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.exception.TCHBusinessException;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.role.dto.RoleDTO;
import uit.javabackend.webclonethecoffeehouse.role.dto.UserGroupDTO;
import uit.javabackend.webclonethecoffeehouse.role.dto.UserGroupWithUsersDTO;
import uit.javabackend.webclonethecoffeehouse.role.model.Role;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;
import uit.javabackend.webclonethecoffeehouse.role.repository.UserGroupRepository;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface UserGroupService extends GenericService<UserGroup, UserGroupDTO, UUID> {
    UserGroupWithUsersDTO addUsers(UUID userGroupId, List<UUID> ids);

    List<UserGroupWithUsersDTO> findAllDtoIncludeUsers();

    List<UserGroup> findByRoleId(UUID roleId);

    void deleteByName(String name);

    public UserGroupDTO update(UserGroupDTO userGroupDTO);

    UserGroupWithUsersDTO removeUsers(UUID userGroupId, List<UUID> ids);

    UserGroupDTO findUserGroupByNameDTO(String name);

    UserGroup findUserGroupByName(String name);

    List<UserGroupDTO> searchUserGroups(String query);
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
                .orElseThrow(() -> new TCHBusinessException("UserGroup is not existed."));

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

        List<UserGroup> userGroupList = repository.findAll();
        List<UserGroupWithUsersDTO> userGroupWithUsersDTOS = userGroupList
                .stream()
                .map(model->tchMapper.map(model,UserGroupWithUsersDTO.class))
                .collect(Collectors.toList());

        return userGroupWithUsersDTOS;
    }

    public UserGroupDTO update(UserGroupDTO userGroupDTO) {
        UserGroup userGroup = repository.findById(userGroupDTO.getId())
                .orElseThrow(() -> new TCHBusinessException("Role not found"));
        userGroup.setName(userGroupDTO.getName());
        userGroup.setCode(userGroupDTO.getCode());
        userGroup.setDescription(userGroupDTO.getDescription());
        return tchMapper.map(userGroup, UserGroupDTO.class);
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
    public List<UserGroupDTO> searchUserGroups(String query) {
        List<UserGroup> userGroups = repository.searchUserGroups(query);
        List<UserGroupDTO> userGroupDTOS = userGroups
                .stream()
                .map(model -> tchMapper.map(model,UserGroupDTO.class))
                .collect(Collectors.toList());
        return userGroupDTOS;
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
