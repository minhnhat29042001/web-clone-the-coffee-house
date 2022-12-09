package uit.javabackend.webclonethecoffeehouse.vnp_payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VnpayQueryDTO {

    // ma id don hang
    private String orderId;

    // ngay giao dich cua don hang can truy van
     private String create_date;
}
