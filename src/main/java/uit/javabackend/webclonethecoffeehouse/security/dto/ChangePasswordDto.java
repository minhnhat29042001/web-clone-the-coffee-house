package uit.javabackend.webclonethecoffeehouse.security.dto;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    String username;
    String oldPassword;
    String newPassword;
}
