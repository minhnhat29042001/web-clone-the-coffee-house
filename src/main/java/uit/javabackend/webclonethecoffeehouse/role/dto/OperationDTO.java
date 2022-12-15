package uit.javabackend.webclonethecoffeehouse.role.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.role.model.Operation;
import uit.javabackend.webclonethecoffeehouse.role.validation.annotation.UniqueOperationCode;
import uit.javabackend.webclonethecoffeehouse.role.validation.annotation.UniqueOperationName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDTO {
    private UUID id;

    @Size(min = 5, max = 100, message = "{operation.name.size}")
    @NotBlank
    @UniqueOperationName
    private String name;

    @Size(min = 3, max = 10, message = "{operation.code.size}")
    @NotBlank
    @UniqueOperationCode
    private String code;

    @NotBlank(message = "{operation.description.blank}")
    private String description;

    private Operation.Type type;
}
