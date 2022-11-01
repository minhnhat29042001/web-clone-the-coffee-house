package uit.javabackend.webclonethecoffeehouse.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.validation.annotation.UniqueUsername;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    @Size(min = 5, max = 100, message = "user.username.size")
    @NotBlank
    @UniqueUsername(message = "user.username.existed")
    private String username;
    @Range(min = 1, message = "user.email.null")
    private String email;
    private String birth;
    private String avatar;
    @Range(min = 1, message = "user.phone.null")
    private String phone;
    private User.Gender gender;
}
