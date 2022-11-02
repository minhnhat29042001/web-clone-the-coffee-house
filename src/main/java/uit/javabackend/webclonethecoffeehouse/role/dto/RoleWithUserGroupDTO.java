package uit.javabackend.webclonethecoffeehouse.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uit.javabackend.webclonethecoffeehouse.role.validation.annotation.UniqueRoleName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleWithUserGroupDTO {
    private UUID id;
    @Size(min = 5, max = 100, message = "{role.name.size}")
    @NotBlank
    @UniqueRoleName
    private String name;
    @Size(min = 3, max = 10, message = "{role.code.size}")
    @NotBlank
    private String code;
    @NotBlank(message = "{role.description.blank}")
    private String description;

    private Set<UserGroupDTO> userGroups;
}
