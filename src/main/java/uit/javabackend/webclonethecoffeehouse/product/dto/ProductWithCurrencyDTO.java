package uit.javabackend.webclonethecoffeehouse.product.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.currency.dto.CurrencyDTO;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithCurrencyDTO {
    private UUID id;
    private String name;
    private String imgUrl;
    private Integer price;
    private String description;
    private CurrencyDTO currency;
}
