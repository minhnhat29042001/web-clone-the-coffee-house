package uit.javabackend.webclonethecoffeehouse.user.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.validation.annotation.CorrectGender;
import uit.javabackend.webclonethecoffeehouse.user.validation.annotation.UniqueEmail;
import uit.javabackend.webclonethecoffeehouse.user.validation.annotation.UniqueUsername;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoWithoutPassword {
    private UUID id;
    private String name;
    @Size(min = 5, max = 100, message = "{user.username.size}")
    @NotBlank(message = "{user.username.blank}")
    private String username;
    @NotBlank(message = "{user.email.blank}")
    private String email;
    private String birth;
    private String avatar;
    private String phone;
    private String address;

    @CorrectGender(message = "{user.gender.incorrect}")
    private String gender;
}
