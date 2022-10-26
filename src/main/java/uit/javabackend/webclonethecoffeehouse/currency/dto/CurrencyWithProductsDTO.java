package uit.javabackend.webclonethecoffeehouse.currency.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyWithProductsDTO {
    private UUID id;
    private String name;
    private Set<ProductDTO> products;

}
