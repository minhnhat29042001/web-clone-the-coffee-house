package uit.javabackend.webclonethecoffeehouse.business.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDiscountWithUserDTO implements Serializable {

    private UUID id;

    private String description;
    private String imageUrl;

    private Integer usedCount;

    private UserDTO user;

}
