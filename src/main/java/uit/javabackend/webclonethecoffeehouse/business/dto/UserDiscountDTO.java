package uit.javabackend.webclonethecoffeehouse.business.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
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

    @Length(min = 5, max = 50, message = "")
    @UniqueUserDiscountDescription
    private String description;
    private String imageUrl;

    private Integer usedCount;

}
