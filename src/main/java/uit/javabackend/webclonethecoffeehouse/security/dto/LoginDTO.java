package uit.javabackend.webclonethecoffeehouse.security.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.security.validation.MustBeExistedUser;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @MustBeExistedUser
    private String username;
    private String password;
}
