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
import uit.javabackend.webclonethecoffeehouse.role.dto.RoleWithOperationsDTO;
import uit.javabackend.webclonethecoffeehouse.role.dto.RoleWithUserGroupDTO;
import uit.javabackend.webclonethecoffeehouse.role.model.Operation;
import uit.javabackend.webclonethecoffeehouse.role.model.Role;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;
import uit.javabackend.webclonethecoffeehouse.role.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface RoleService extends GenericService<Role, RoleDTO, UUID> {
    //Role update(Role role, String code);

    void deleteByCode(String code);

    RoleDTO save(RoleDTO dto);

    RoleWithOperationsDTO addOperations(UUID roleId, List<UUID> ids);

    RoleWithOperationsDTO removeOperations(UUID roleId, List<UUID> ids);

    RoleWithUserGroupDTO addUserGroup(UUID roleId, List<UUID> ids);

    RoleWithUserGroupDTO removeUserGroup(UUID roleId, List<UUID> ids);

    List<RoleWithOperationsDTO> getOperationsWithRoleId(UUID roleId);

    List<RoleWithUserGroupDTO> getUserGroupsWithRoleId(UUID roleId);

    RoleDTO update(RoleDTO roleDTO);

    List<RoleWithOperationsDTO> findAllWithOperations();

    List<RoleWithUserGroupDTO> findAllWitUsergroups();

    List<RoleDTO> searchRole(String query);
}

@Service
@Transactional
class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final TCHMapper mapper;
    private final OperationService operationService;
    private final UserGroupService userGroupService;
    private final String roleNotExistMessage = "Role is not existed.";

    public RoleServiceImpl(RoleRepository repository, TCHMapper mapper, OperationService operationService, UserGroupService userGroupService) {
        this.repository = repository;
        this.mapper = mapper;
        this.operationService = operationService;
        this.userGroupService = userGroupService;
    }

//    @Override
//    public Role update(Role role, String code) {
//        Role curRole = repository.findByCode(code);
//        curRole.setName(role.getName());
//        curRole.setDescription(role.getDescription());
//        return repository.save(curRole);
//    }

    @Override
    public void deleteByCode(String code) {
        repository.deleteByCode(code);
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        Optional<Role> findRole = repository.findByCode(roleDTO.getCode());
        if(findRole.isPresent()){
            throw new TCHBusinessException("role code is existed");
        }
        Role model = mapper.map(roleDTO, Role.class);
        return mapper.map(repository.save(model), RoleDTO.class);
    }

    @Override
    public RoleWithOperationsDTO addOperations(UUID roleId, List<UUID> ids) {
        Role curRole = repository.findById(roleId)
                .orElseThrow(() ->
                        new TCHBusinessException(roleNotExistMessage)
                );

        List<Operation> operations = operationService.findByIds(ids);

        operations.forEach(curRole::addOperation);

        return mapper.map(curRole, RoleWithOperationsDTO.class);
    }

    @Override
    public RoleWithOperationsDTO removeOperations(UUID roleId, List<UUID> ids) {
        Role curRole = repository.findById(roleId)
                .orElseThrow(() ->
                        new TCHBusinessException(roleNotExistMessage)
                );

        List<Operation> operations = operationService.findByIds(ids);

        operations.forEach(curRole::removeOperation);

        return mapper.map(curRole, RoleWithOperationsDTO.class);
    }

    @Override
    public List<RoleWithOperationsDTO> getOperationsWithRoleId(UUID roleId) {
        List<RoleWithOperationsDTO> operationsDTOs = new ArrayList<>();
        if (!repository.existsById(roleId))
            throw new TCHBusinessException(roleNotExistMessage);
        List<Operation> operations = operationService.findByRoleId(roleId);
        operations.forEach(
                operation -> operationsDTOs.add(mapper.map(operation, RoleWithOperationsDTO.class))
        );
        return operationsDTOs;
    }

    @Override
    public RoleWithUserGroupDTO addUserGroup(UUID roleId, List<UUID> ids) {
        Role curRole = repository.findById(roleId)
                .orElseThrow(() ->
                        new TCHBusinessException(roleNotExistMessage)
                );

        List<UserGroup> userGroups = userGroupService.findByIds(ids);

        userGroups.forEach(curRole::addUserGroup);

        return mapper.map(curRole, RoleWithUserGroupDTO.class);
    }

    @Override
    public RoleWithUserGroupDTO removeUserGroup(UUID roleId, List<UUID> ids) {
        Role curRole = repository.findById(roleId)
                .orElseThrow(() ->
                        new TCHBusinessException(roleNotExistMessage)
                );

        List<UserGroup> userGroups = userGroupService.findByIds(ids);

        userGroups.forEach(curRole::removeUserGroup);

        return mapper.map(curRole, RoleWithUserGroupDTO.class);
    }

    @Override
    public List<RoleWithUserGroupDTO> getUserGroupsWithRoleId(UUID roleId) {
        List<RoleWithUserGroupDTO> userGroupDTOs = new ArrayList<>();
        if (!repository.existsById(roleId))
            throw new TCHBusinessException(roleNotExistMessage);
        List<UserGroup> userGroups = userGroupService.findByRoleId(roleId);
        userGroups.forEach(
                userGroup -> userGroupDTOs.add(mapper.map(userGroup, RoleWithUserGroupDTO.class))
        );
        return userGroupDTOs;
    }

    @Override
    public RoleDTO update(RoleDTO roleDTO) {
        Role role = repository.findById(roleDTO.getId())
                .orElseThrow(() -> new TCHBusinessException("Role not found"));
        role.setName(roleDTO.getName());
        role.setCode(roleDTO.getCode());
        role.setDescription(roleDTO.getDescription());
        return mapper.map(role, RoleDTO.class);
    }

    @Override
    public List<RoleWithOperationsDTO> findAllWithOperations() {
        List<Role> roles = repository.findAll();
        List<RoleWithOperationsDTO> roleWithOperationsDTOs = new ArrayList<>();
        roles.forEach(
                role -> {
                    RoleWithOperationsDTO roleWithOperationsDTO = mapper.map(role, RoleWithOperationsDTO.class);
                    roleWithOperationsDTOs.add(roleWithOperationsDTO);
                }
        );
        return roleWithOperationsDTOs;
    }

    @Override
    public List<RoleWithUserGroupDTO> findAllWitUsergroups() {
        List<Role> roles = repository.findAll();
        List<RoleWithUserGroupDTO> roleWithUserGroupDTOs = new ArrayList<>();
        roles.forEach(
                role -> {
                    RoleWithUserGroupDTO roleWithUserGroupDTO = mapper.map(role, RoleWithUserGroupDTO.class);
                    roleWithUserGroupDTOs.add(roleWithUserGroupDTO);
                }
        );
        return roleWithUserGroupDTOs;
    }

    @Override
    public List<RoleDTO> searchRole(String query) {
        List<Role> roles = repository.searchRoles(query);
        List<RoleDTO> roleDTOS = roles
                .stream()
                .map(product -> mapper.map(product,RoleDTO.class))
                .collect(Collectors.toList());
        return roleDTOS;
    }

    @Override
    public JpaRepository<Role, UUID> getRepository() {
        return this.repository;
    }

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }
}
