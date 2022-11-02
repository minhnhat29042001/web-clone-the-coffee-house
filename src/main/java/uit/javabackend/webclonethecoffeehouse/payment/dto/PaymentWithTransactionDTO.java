package uit.javabackend.webclonethecoffeehouse.payment.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.payment.model.Payment;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentWithTransactionDTO {
    private String name;
    private String code;
    private String description;
    private Payment.MethodPayment method;
    private String partnerCode;
    private String privateKey;
    private String publicKey;
    private List<TransactionDTO> transactions;
}
