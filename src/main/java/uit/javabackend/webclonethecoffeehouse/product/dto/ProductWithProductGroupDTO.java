package uit.javabackend.webclonethecoffeehouse.product.dto;

import lombok.*;

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
    private ProductGroupDTO productGroup;
}
