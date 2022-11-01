package uit.javabackend.webclonethecoffeehouse.orderproduct.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderDTO;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductWithOrderDTO {
    private UUID id;
    private String name;
    private int totalPrice;
    private int quantity;
    private OrderDTO orderDTO;
}
