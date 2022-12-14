package uit.javabackend.webclonethecoffeehouse.order.dto;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {

    private UUID id;
    private String note;
    private String size;
    private String topping;
    private int totalPrice;
    private int quantity;

}
