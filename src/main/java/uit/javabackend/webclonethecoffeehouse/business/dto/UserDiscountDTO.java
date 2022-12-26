package uit.javabackend.webclonethecoffeehouse.business.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import uit.javabackend.webclonethecoffeehouse.business.validation.annotation.UniqueUserDiscountDescription;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDiscountDTO implements Serializable {

    private UUID id;

    @Length(min = 5, max = 50, message = "description between {min} and {max}")
    private String description;

    @Range(min = 1, message = "usedcount at least {min}")
    private Integer usedCount;
    private UUID userId;
    private UUID discountId;

}
