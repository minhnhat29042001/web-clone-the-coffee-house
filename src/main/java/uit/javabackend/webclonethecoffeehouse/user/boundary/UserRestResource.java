package uit.javabackend.webclonethecoffeehouse.user.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/UsersManagement")
public class UserRestResource {
    private final UserService userService;

    public UserRestResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/GetAllUser")
    public ResponseEntity<?> findAllUser() {
        return ResponseUtil.get(
                userService.findAllDto(UserDTO.class)
                , HttpStatus.OK
        );
    }


    @GetMapping("/GetAllUserGroupUsername")
    public ResponseEntity<?> findAllUserGroupUsername(@RequestParam("username") String username) {
        return ResponseUtil.get(
                userService.findAllUserGroupUsername(username)
                , HttpStatus.OK
        );
    }


    @PostMapping("/SaveUser")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseUtil.get(
                userService.save(userDTO, User.class, UserDTO.class)
                , HttpStatus.OK
        );
    }

    @PutMapping("/UpdateUser")
    public Object update(@RequestBody UserDTO user) {
        return ResponseUtil.get(userService.update(user), HttpStatus.OK);
    }

    @DeleteMapping("/DeleteUser")
    public Object delete(@RequestParam("username") String username) {
        userService.deleteByUserName(username);
        return HttpStatus.OK;
    }
}
