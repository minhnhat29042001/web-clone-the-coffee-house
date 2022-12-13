package uit.javabackend.webclonethecoffeehouse.user.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;
import uit.javabackend.webclonethecoffeehouse.user.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/UsersManagement")
public class UserRestResource {
    private final UserService userService;

    public UserRestResource(UserService userService) {
        this.userService = userService;
    }

    @TCHOperation(name = "HighUserManagement")
    @GetMapping("/GetAllUser")
    public Object findAllUser() {
        return ResponseUtil.get(
                userService.findAllDto(UserDTO.class)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "HighUserManagement")
    @GetMapping("/GetAllUserGroupByUsername")
    public Object findAllUserGroupUsername(@RequestParam("username") String username) {
        return ResponseUtil.get(
                userService.findAllUserGroupUsername(username)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "HighUserManagement")
    @PostMapping("/SaveUser")
    public Object saveUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseUtil.get(
                userService.createUser(userDTO)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "LowUserManagement")
    @PutMapping("/UpdateUser")
    public Object update(@RequestBody UserDTO user) {
        return ResponseUtil.get(userService.update(user), HttpStatus.OK);
    }

    @TCHOperation(name = "HighUserManagement")
    @DeleteMapping("/DeleteUser")
    public Object delete(@RequestParam("username") String username) {
        userService.deleteByUserName(username);
        return HttpStatus.OK;
    }
}
