package uit.javabackend.webclonethecoffeehouse.payment.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderDTO;
import uit.javabackend.webclonethecoffeehouse.payment.model.Transaction;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionWithOrderDTO {
    private UUID id;
    private String content;
    private Transaction.Status status;
    private OrderDTO order;
}
