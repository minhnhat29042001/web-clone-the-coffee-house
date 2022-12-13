package uit.javabackend.webclonethecoffeehouse.product.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import uit.javabackend.webclonethecoffeehouse.product.validation.annotation.UniqueProductName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

        private UUID id;

        @Size(min = 5 , max = 100,message = "product.name.size")
        @NotBlank
        @UniqueProductName(message = "product.name.existed")
        private String name;


        private String imgUrl;

        @Range(min = 1, message= "product.price.null")
        private Integer price;

        @NotBlank(message = "product.description.blank")
        private String description;
}
