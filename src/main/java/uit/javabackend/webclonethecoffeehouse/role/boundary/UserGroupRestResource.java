package uit.javabackend.webclonethecoffeehouse.role.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.role.dto.UserGroupDTO;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;
import uit.javabackend.webclonethecoffeehouse.role.service.UserGroupService;

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

    @GetMapping("/GetAllUserGroups")
    public ResponseEntity<?> findAllUserGroup() {
        return ResponseUtil.get(
                service.findAllDto(UserGroupDTO.class)
                , HttpStatus.OK
        );
    }

    @GetMapping("/GetAllUserGroupIncludeUsers")
    public ResponseEntity<?> findAllUserGroupIncludedUsers() {
        return ResponseUtil.get(
                service.findAllDtoIncludeUsers()
                , HttpStatus.OK
        );
    }

    @PostMapping("/SaveUserGroup")
    public ResponseEntity<?> saveUserGroup(@RequestBody @Valid UserGroupDTO userGroupDto) {
        return ResponseUtil.get(
                service.save(userGroupDto, UserGroup.class, UserGroupDTO.class)
                , HttpStatus.OK
        );
    }

    @PostMapping("{user-group-id}/AddUsers")
    public ResponseEntity<?> addUsers(
            @PathVariable("user-group-id") UUID userGroupId,
            @RequestBody List<UUID> ids) {
        return ResponseUtil.get(
                service.addUsers(userGroupId, ids)
                , HttpStatus.OK
        );
    }

    @PutMapping("/UpdateUserGroup")
    public Object update(@RequestBody UserGroupDTO userGroupDTO) {
        return ResponseUtil.get(service.update(userGroupDTO), HttpStatus.OK);
    }

    @DeleteMapping("/DeleteUserGroup")
    public Object delete(@RequestParam("name") String name) {
        service.deleteByName(name);
        return HttpStatus.OK;
    }
}
