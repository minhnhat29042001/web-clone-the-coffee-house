package uit.javabackend.webclonethecoffeehouse.product.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.product.validation.annotation.UniqueProductGroupName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductGroupDTO {
    private UUID id;

    @Size(min = 3,max = 100,message = "{productGroup.name.size}")
    @NotBlank(message = "{productGroup.name.blank}")
    @UniqueProductGroupName(message = "{productGroup.name.existed}")
    private String name;
}
