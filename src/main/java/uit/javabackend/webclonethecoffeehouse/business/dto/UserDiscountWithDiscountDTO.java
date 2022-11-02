package uit.javabackend.webclonethecoffeehouse.business.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDiscountWithDiscountDTO implements Serializable {

    private UUID id;

    @Length(min = 5, max = 50, message = "")
    private String description;

    private Integer usedCount;

    private DiscountDTO discountDTO;
}
