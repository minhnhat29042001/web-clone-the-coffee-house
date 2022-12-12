package uit.javabackend.webclonethecoffeehouse.order.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.order.enums.OrderStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderWithOrderProductsDTO {
    private UUID id;

    @Size(min = 5 , max = 100,message = "{order.name.size}")
    @NotBlank
    private String CustomerName;

    @NotBlank
    private String address;

    private String note;

    private String codeCoupon;

    private Integer totalPrice;

    private OrderStatus status;

    private List<OrderProductDTO> orderProductDtos = new ArrayList<>();
}
