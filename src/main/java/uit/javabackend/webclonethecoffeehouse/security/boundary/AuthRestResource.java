package uit.javabackend.webclonethecoffeehouse.security.boundary;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.security.dto.LoginDTO;
import uit.javabackend.webclonethecoffeehouse.security.service.AuthService;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthRestResource {
    private final AuthService authService;

    public AuthRestResource(AuthService authService) {
        this.authService = authService;
    }


    @Operation(summary = "validate a jwt token")
    @PostMapping("/validateToken")
    public Object validateToken(@RequestParam String token) {
        return ResponseUtil.get(
                authService.validateToken(token)
                , HttpStatus.OK
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        return ResponseUtil.get(authService.login(loginDTO), HttpStatus.OK);
    }

    @Operation(summary = "Register a new customer account")
    @PostMapping("/register")
    public Object registerCustomer(@RequestBody @Valid UserDTO userDTO) {
        return ResponseUtil.get(
                authService.registerCustomer(userDTO)
                , HttpStatus.OK
        );
    }

    @Operation(summary = "Forgot password")
    @PostMapping("/resetPassword")
    public Object resetPassword(@RequestParam String host, @RequestParam String email) {
        return ResponseUtil.get(
                authService.resetPassword(host, email)
                , HttpStatus.OK
        );
    }

    @Operation(summary = "Change password")
    @PostMapping("/changePassword")
    public Object changePassword(@RequestParam String token, @RequestParam String password) {
        return ResponseUtil.get(
                authService.changePassword(token, password)
                , HttpStatus.OK
        );
    }
}
