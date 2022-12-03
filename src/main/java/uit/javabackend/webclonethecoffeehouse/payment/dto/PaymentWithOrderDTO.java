package uit.javabackend.webclonethecoffeehouse.payment.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.payment.model.Payment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentWithOrderDTO {
    private String name;
    private String code;
    private String description;
    private Payment.MethodPayment method;

}
