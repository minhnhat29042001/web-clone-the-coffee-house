package uit.javabackend.webclonethecoffeehouse.product.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.product.model.ProductEntity;
import uit.javabackend.webclonethecoffeehouse.product.validation.annotation.UniqueProductName;

import javax.persistence.Column;
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

        @NotBlank
        private String productUrl;

        @NotBlank
        private Integer price;

        @NotBlank
        private String currencyId;

        @NotBlank
        private String imgUrl;

        @NotBlank
        private String collectionId;

        @NotBlank(message = "product.description.blank")
        @Size(min = 5 , max = 100,message = "Product description must have length between {min} and {max}")
        private String description;
}
