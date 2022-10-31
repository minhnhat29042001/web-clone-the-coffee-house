package uit.javabackend.webclonethecoffeehouse.transaction.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

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

        @Size(min = 5, max = 100, message = "order.name.size")
        @NotBlank
        private String name;

        @NotBlank
        private String address;

        private String note;

        private boolean useCoupon;

        @Range(min = 1, message = "product.price.null")
        private Integer totalPrice;

}
