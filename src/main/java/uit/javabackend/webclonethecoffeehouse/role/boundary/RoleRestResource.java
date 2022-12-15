package uit.javabackend.webclonethecoffeehouse.role.boundary;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.role.dto.RoleDTO;
import uit.javabackend.webclonethecoffeehouse.role.service.RoleService;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/RolesManagement")
public class RoleRestResource {
    private final RoleService service;

    public RoleRestResource(RoleService roleService) {
        this.service = roleService;
    }

    @TCHOperation(name = "GetAllRoles")
    @GetMapping("/GetAllRoles")
    public Object findAll() {
        return ResponseUtil.get(service.findAllDto(RoleDTO.class), HttpStatus.OK);
    }

    @TCHOperation(name = "GetAllRoles")
    @GetMapping("/GetAllRolesPaging")
    public Object findAllDtoPaging(@RequestParam("size") int size,
                                   @RequestParam("index") int index) {
        return ResponseUtil.get(
                service.findAllDto(Pageable.ofSize(size).withPage(index), RoleDTO.class)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "GetOperationsWithRoleId")
    @GetMapping("{role-id}/GetOperationsWithRoleId")
    public ResponseEntity<?> getOperationsWithRole(
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.getOperationsWithRoleId(roleId)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "GetUserGroupsWithRoleId")
    @GetMapping("{role-id}/GetUserGroupsWithRoleId")
    public ResponseEntity<?> getUserGroupsWithRole(
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.getUserGroupsWithRoleId(roleId)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "SaveRole")
    @PostMapping("/SaveRole")
    public Object save(@RequestBody @Valid RoleDTO roleDTO) {
        return ResponseUtil.get(service.save(roleDTO), HttpStatus.CREATED);
    }

    @TCHOperation(name = "AddOperationsToRole")
    @PostMapping("{role-id}/AddOperations")
    public ResponseEntity<?> addOperations(
            @RequestBody List<UUID> ids,
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.addOperations(roleId, ids)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "RemoveOperationsFromRole")
    @DeleteMapping("{role-id}/RemoveOperations")
    public ResponseEntity<?> removeOperations(
            @RequestBody List<UUID> ids,
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.removeOperations(roleId, ids)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "AddUserGroupIntoRole")
    @PostMapping("{role-id}/addUserGroup")
    public ResponseEntity<?> addUserGroup(
            @RequestBody List<UUID> ids,
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.addUserGroup(roleId, ids)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "UpdateRole")
    @PutMapping("/UpdateRole")
    public Object update(@RequestBody RoleDTO roleDTO) {
        return ResponseUtil.get(service.update(roleDTO), HttpStatus.OK);
    }

    @TCHOperation(name = "RemoveUserGroupFromRole")
    @DeleteMapping("{role-id}/removeUserGroup")
    public ResponseEntity<?> removeUserGroup(
            @RequestBody List<UUID> ids,
            @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                service.removeUserGroup(roleId, ids)
                , HttpStatus.OK
        );
    }

}
