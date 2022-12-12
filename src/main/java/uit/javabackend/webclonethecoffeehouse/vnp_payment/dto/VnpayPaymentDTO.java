package uit.javabackend.webclonethecoffeehouse.vnp_payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VnpayPaymentDTO {

    private UUID id;

    private String vnp_Version;

    private String vnp_Command;

    private String vnp_TmnCode;

    private String vnp_Amount;

    private String vnp_BankCode;

    private String vnp_CreateDate;

    private String vnp_CurrCode;

    private String vnp_IpAddr;

    private String vnp_Locale;

    private String vnp_OrderInfo;

    private String vnp_OrderType;

    private String vnp_ExpireDate;

    private String vnp_TxnRef;

    private String vnp_BankTranNo;

    private String vnp_CardType;

    private String vnp_PayDate;

    private String vnp_TransactionNo;

    private String vnp_TransactionStatus;

    private String vnp_ResponseCode;

}
