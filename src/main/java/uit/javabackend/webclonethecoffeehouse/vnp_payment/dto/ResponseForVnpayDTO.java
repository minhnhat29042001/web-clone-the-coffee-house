package uit.javabackend.webclonethecoffeehouse.vnp_payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForVnpayDTO {
    private String code;
    private String message;
    private Object data;
}
