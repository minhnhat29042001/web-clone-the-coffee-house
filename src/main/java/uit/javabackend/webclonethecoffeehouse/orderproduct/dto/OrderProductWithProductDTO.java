package uit.javabackend.webclonethecoffeehouse.orderproduct.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.currency.dto.CurrencyDTO;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductWithProductDTO {
    private UUID id;
    private String name;
    private int totalPrice;
    private int quantity;
    private ProductDTO productDTO;
}
