package uit.javabackend.webclonethecoffeehouse.payment.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.payment.model.Payment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private String name;
    private String code;
    private String description;
    private Payment.MethodPayment method;
    private String partnerCode;
    private String privateKey;
    private String publicKey;
}
