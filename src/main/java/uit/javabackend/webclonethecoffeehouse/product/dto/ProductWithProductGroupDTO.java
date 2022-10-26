package uit.javabackend.webclonethecoffeehouse.product.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.currency.dto.CurrencyDTO;
import uit.javabackend.webclonethecoffeehouse.productgroup.dto.ProductGroupDTO;
import uit.javabackend.webclonethecoffeehouse.productgroup.model.ProductGroup;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithProductGroupDTO {
    private UUID id;
    private String name;
    private String imgUrl;
    private Integer price;
    private String description;
    private ProductGroupDTO productGroupDTO;
}
