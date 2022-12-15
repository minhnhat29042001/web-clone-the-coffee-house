package uit.javabackend.webclonethecoffeehouse.role.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.role.dto.UserGroupDTO;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;
import uit.javabackend.webclonethecoffeehouse.role.service.UserGroupService;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/UserGroupsManagement")
public class UserGroupRestResource {
    private final UserGroupService service;

    public UserGroupRestResource(UserGroupService service) {
        this.service = service;
    }

    @TCHOperation(name = "GetAllUserGroups")
    @GetMapping("/GetAllUserGroups")
    public ResponseEntity<?> findAllUserGroup() {
        return ResponseUtil.get(
                service.findAllDto(UserGroupDTO.class)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "GetAllUserGroupIncludeUsers")
    @GetMapping("/GetAllUserGroupIncludeUsers")
    public ResponseEntity<?> findAllUserGroupIncludedUsers() {
        return ResponseUtil.get(
                service.findAllDtoIncludeUsers()
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "GetUserGroupByName")
    @GetMapping("/GetUserGroupByName")
    public ResponseEntity<?> findUserGroupByName(@RequestParam("name") String name) {
        return ResponseUtil.get(
                service.findUserGroupByNameDTO(name)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "SaveUserGroup")
    @PostMapping("/SaveUserGroup")
    public ResponseEntity<?> saveUserGroup(@RequestBody @Valid UserGroupDTO userGroupDto) {
        return ResponseUtil.get(
                service.save(userGroupDto, UserGroup.class, UserGroupDTO.class)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "AddUsersIntoGroup")
    @PostMapping("{user-group-id}/AddUsers")
    public ResponseEntity<?> addUsers(
            @PathVariable("user-group-id") UUID userGroupId,
            @RequestBody List<UUID> ids) {
        return ResponseUtil.get(
                service.addUsers(userGroupId, ids)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "RemoveUsersFromGroup")
    @PostMapping("{user-group-id}/RemoveUsers")
    public ResponseEntity<?> removeUsers(
            @PathVariable("user-group-id") UUID userGroupId,
            @RequestBody List<UUID> ids) {
        return ResponseUtil.get(
                service.removeUsers(userGroupId, ids)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "UpdateUserGroup")
    @PutMapping("/UpdateUserGroup")
    public Object update(@RequestBody UserGroupDTO userGroupDTO) {
        return ResponseUtil.get(service.update(userGroupDTO), HttpStatus.OK);
    }

    @TCHOperation(name = "DeleteUserGroup")
    @DeleteMapping("/DeleteUserGroup")
    public Object delete(@RequestParam("name") String name) {
        service.deleteByName(name);
        return HttpStatus.OK;
    }
}
