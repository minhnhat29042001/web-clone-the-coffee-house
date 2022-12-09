package uit.javabackend.webclonethecoffeehouse.vnp_payment.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VnpayPaymentEntity {

    @UtilityClass
    public static class VnpayPayment{
        public static final String VNP_VERSION = "VNP_VERSION";
        public static final String TABLE_NAME = "TCH_VNPAY_PAYMENT";
        public static final String VNP_COMMAND = "VNP_COMMAND";
        public static final String VNP_TMN_CODE = "VNP_TMN_CODE";
        public static final String VNP_AMOUNT = "VNP_AMOUNT";
        public static final String VNP_BANK_CODE = "VNP_BANK_CODE";
        public static final String VNP_CREATE_DATE = "VNP_CREATE_DATE";
        public static final String VNP_CURRENCY_CODE = "VNP_CURRENCY_CODE";
        public static final String VNP_IP_ADDRESS = "VNP_IP_ADDRESS";
        public static final String VNP_LOCALE = "VNP_LOCALE";
        public static final String VNP_ORDER_INFO = "VNP_ORDER_INFO";
        public static final String VNP_ORDER_TYPE = "VNP_ORDER_TYPE";
        public static final String VNP_EXPIRE_DATE = "VNP_EXPIRE_DATE";
        public static final String VNP_TXN_REF = "VNP_TXN_REF";
        public static final String VNP_BANK_TRANNO = "VNP_BANK_TRANNO";
        public static final String VNP_CARD_TYPE = "VNP_CARD_TYPE";
        public static final String VNP_PAY_DATE = "VNP_PAY_DATE";
        public static final String VNP_TRANSACTION_NO = "VNP_TRANSACTION_NO";
        public static final String VNP_TRANSACTION_STATUS = "VNP_TRANSACTION_STATUS";
        public static final String VNP_RESPONSE_CODE = "VNP_RESPONSE_CODE";

    }
    @UtilityClass
    public static class VnpayPaymentMapped{
        public static final String VnpayPaymentMappedByOrder = "vnpayPayment";
    }
}
