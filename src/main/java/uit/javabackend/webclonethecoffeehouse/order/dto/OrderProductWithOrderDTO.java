package uit.javabackend.webclonethecoffeehouse.order.dto;

import lombok.*;

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
