package uit.javabackend.webclonethecoffeehouse.order.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductWithOrderProductDTO;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductWithProductDTO {
    private UUID id;
    private String note;
    private int totalPrice;
    private int quantity;
    private ProductWithOrderProductDTO productDTO;
}
