package uit.javabackend.webclonethecoffeehouse.vnp_payment.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VnpPaymentCreateDTO {

    private UUID orderId;
    private int amount;
    private String description;
    private String bankcode;

}
