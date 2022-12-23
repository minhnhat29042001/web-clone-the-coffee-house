package uit.javabackend.webclonethecoffeehouse.user.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;
import uit.javabackend.webclonethecoffeehouse.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/UsersManagement")
@CrossOrigin(origins = "*")
public class UserRestResource {
    private final UserService userService;

    public UserRestResource(UserService userService) {
        this.userService = userService;
    }

    @TCHOperation(name = "GetAllUser")
    @GetMapping("/GetAllUser")
    public Object findAllUser() {
        return ResponseUtil.get(
                userService.findAllDto(UserDTO.class)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "GetUserBySpecificField")
    @GetMapping("/GetUserByUsername")
    public Object getUserByUsername(@RequestParam String username) {
        return ResponseUtil.get(
                userService.getUserByUsername(username)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "GetAllUserGroupByUsername")
    @GetMapping("/GetAllUserGroupByUsername")
    public Object findAllUserGroupUsername(@RequestParam("username") String username) {
        return ResponseUtil.get(
                userService.findAllUserGroupUsername(username)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "SaveUser")
    @PostMapping("/SaveUser")
    public Object saveUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseUtil.get(
                userService.createUser(userDTO)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "UpdateUser")
    @PostMapping(path = "/saveUserAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object saveUserAvatar(@RequestParam("username") String username, @RequestPart("avatar") MultipartFile avatar, HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        return ResponseUtil.get(userService.saveUserAvatar(username, avatar, baseUrl), HttpStatus.CREATED);
    }

    @TCHOperation(name = "UpdateUser")
    @PutMapping("/UpdateUser")
    public Object update(@RequestBody UserDTO user) {
        return ResponseUtil.get(userService.update(user), HttpStatus.OK);
    }

    @TCHOperation(name = "DeleteUser")
    @DeleteMapping("/DeleteUser")
    public Object delete(@RequestParam("username") String username) {
        userService.deleteByUserName(username);
        return HttpStatus.OK;
    }
}
