package uit.javabackend.webclonethecoffeehouse.security.boundary;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.security.dto.LoginDTO;
import uit.javabackend.webclonethecoffeehouse.security.service.AuthService;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthRestResource {
    private final AuthService authService;

    public AuthRestResource(AuthService authService) {
        this.authService = authService;
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
}
