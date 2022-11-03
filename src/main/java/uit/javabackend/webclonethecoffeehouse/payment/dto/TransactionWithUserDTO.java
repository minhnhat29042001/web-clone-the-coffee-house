package uit.javabackend.webclonethecoffeehouse.payment.dto;

import lombok.*;
import uit.javabackend.webclonethecoffeehouse.payment.model.Transaction;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionWithUserDTO {
    private UUID id;
    private String content;
    private Transaction.Status status;
    private UserDTO user;
}
