package uit.javabackend.webclonethecoffeehouse.business.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountCheckDTO {
    private String code;
    private Integer totalPriceOfOrder;
}
