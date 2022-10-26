package uit.javabackend.webclonethecoffeehouse.currency.dto;


import lombok.*;
import uit.javabackend.webclonethecoffeehouse.currency.validation.annotation.UniqueCurrencyName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyDTO {
    private UUID id;

    @Size(min = 3,max = 100,message = "currency.name.size")
    @NotBlank(message = "currency.name.blank")
    @UniqueCurrencyName(message = "currency.name.existed")
    private String name;


}
