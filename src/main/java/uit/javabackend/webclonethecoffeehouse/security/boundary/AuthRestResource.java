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

@CrossOrigin(origins = "*")
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
    public Object login(@RequestBody @Valid LoginDTO loginDTO) {
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
    @PostMapping("/forgotPassword")
    public Object forgotPassword(@RequestParam String email, HttpServletRequest request) {
        return ResponseUtil.get(
                authService.forgotPassword(email, request.getScheme() + "://" + request.getHeader("Host"))
                , HttpStatus.OK
        );
    }
    @Operation(summary = "reset password")
    @GetMapping("/resetPassword")
    public Object resetPassword(@RequestParam String code) {
        return ResponseUtil.get(
                authService.resetPassword(code)
                , HttpStatus.OK
        );
    }

    @Operation(summary = "Change password")
    @PostMapping("/changePassword")
    public Object changePassword(@RequestParam String token, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return ResponseUtil.get(
                authService.changePassword(token, newPassword, oldPassword)
                , HttpStatus.OK
        );
    }
}
