package uit.javabackend.webclonethecoffeehouse.vnp_payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;

import javax.persistence.*;

@Entity
@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@Table(name = VnpayPaymentEntity.VnpayPayment.TABLE_NAME)
public class VnpayPayment extends BaseEntity {

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_VERSION)
    private String vnp_Version;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_COMMAND)
    private String vnp_Command;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_TMN_CODE)
    private String vnp_TmnCode;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_AMOUNT)
    private String vnp_Amount;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_BANK_CODE)
    private String vnp_BankCode;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_CREATE_DATE)
    private String vnp_CreateDate;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_CURRENCY_CODE)
    private String vnp_CurrCode;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_IP_ADDRESS)
    private String vnp_IpAddr;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_LOCALE)
    private String vnp_Locale;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_ORDER_INFO)
    private String vnp_OrderInfo;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_ORDER_TYPE)
    private String vnp_OrderType;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_EXPIRE_DATE)
    private String vnp_ExpireDate;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_TXN_REF)
    private String vnp_TxnRef;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_BANK_TRANNO)
    private String vnp_BankTranNo;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_CARD_TYPE)
    private String vnp_CardType;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_PAY_DATE)
    private String vnp_PayDate; //Thời gian thanh toán. Định dạng: yyyyMMddHHmmss

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_TRANSACTION_NO)
    private String vnp_TransactionNo;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_TRANSACTION_STATUS)
    private String vnp_TransactionStatus;

    @Column(name = VnpayPaymentEntity.VnpayPayment.VNP_RESPONSE_CODE)
    private String vnp_ResponseCode;

    @OneToOne(mappedBy = VnpayPaymentEntity.VnpayPaymentMapped.VnpayPaymentMappedByOrder
            , cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    private Order order;

    public void setOrder(Order order) {
        if (order == null) {
            if (this.order != null) {
                this.order.setVnpayPayment(null);
            }
        }
        else {
            order.setVnpayPayment(this);
        }
        this.order = order;
    }
}
