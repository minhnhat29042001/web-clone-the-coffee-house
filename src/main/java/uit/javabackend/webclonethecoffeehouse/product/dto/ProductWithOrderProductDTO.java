package uit.javabackend.webclonethecoffeehouse.product.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithOrderProductDTO  {
    private UUID id;
    private String name;
    private Integer price;

}
