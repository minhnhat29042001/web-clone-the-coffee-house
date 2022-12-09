package uit.javabackend.webclonethecoffeehouse.order.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import uit.javabackend.webclonethecoffeehouse.order.enums.OrderStatus;
import uit.javabackend.webclonethecoffeehouse.product.validation.annotation.UniqueProductName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

        private UUID id;

        @Size(min = 5 , max = 100,message = "order.name.size")
        @NotBlank
        private String customerName;

        @NotBlank
        private String address;

        private String note;

        private String codeCoupon;

        @Range(min = 1, message= "product.price.null")
        private Integer totalPrice;

        private OrderStatus status;

}
