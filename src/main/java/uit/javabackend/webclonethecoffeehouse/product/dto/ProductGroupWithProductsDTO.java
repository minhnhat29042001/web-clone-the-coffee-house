package uit.javabackend.webclonethecoffeehouse.product.dto;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductGroupWithProductsDTO {
    private UUID id;
    private String name;
    private Set<ProductDTO> products;
}
