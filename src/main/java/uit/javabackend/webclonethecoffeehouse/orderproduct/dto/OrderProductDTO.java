package uit.javabackend.webclonethecoffeehouse.orderproduct.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {

    private UUID id;
    private String name;
    private int totalPrice;
    private int quantity;

}
