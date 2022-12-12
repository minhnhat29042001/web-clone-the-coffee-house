package uit.javabackend.webclonethecoffeehouse.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;
import uit.javabackend.webclonethecoffeehouse.role.validation.annotation.UniqueUserGroupName;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link UserGroup} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupDTO implements Serializable {
    private UUID id;
    @Length(min = 5, max = 100, message = "{usergroup.name.size}")
    @UniqueUserGroupName
    private String name;
    @Length(min = 3, max = 10, message = "{usergroup.code.size}")
    private String code;
    @NotBlank(message = "{usergroup.description.blank}")
    private String description;
}
