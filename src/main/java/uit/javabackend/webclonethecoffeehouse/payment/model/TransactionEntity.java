package uit.javabackend.webclonethecoffeehouse.payment.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionEntity {
    @UtilityClass
    public static class Transaction {
        public static final String TABLE_NAME = "TCH_ORDER";
        public static final String USER_ID = "USER_ID";
        public static final String ORDER_ID = "ORDER_ID";
        public static final String PAYMENT_ID = "PAYMENT_ID";
        public static final String STATUS_ID = "STATUS_ID";
    }

    @UtilityClass
    public static class TransactionStatus {
        public static final String TABLE_NAME = "TCH_TRANSACTION_STATUS";
        public static final String status = "P_STATUS";
    }

    @UtilityClass
    public static class Payment {
        public static final String NAME = "P_STATUS";
        public static final String CODE = "P_CODE";
        public static final String DESCRIPTION = "P_DESCRIPTION";
        public static final String METHOD = "P_METHOD";
        public static final String URL = "P_URL";
        public static final String PARTNER_CODE = "P_PARTNER_CODE";
        public static final String PRIVATE_KEY = "P_PRIVATE_KEY";
        public static final String PUBLIC_KEY = "P_PUBLIC_KEY";
        public String TABLE_NAME = "TCH_TRANSACTION_STATUS";
    }


    @UtilityClass
    public static class TransactionMapped {
        public static final String TRANSACTION_MAPPED_USER = "user";
        public static final String TRANSACTION_MAPPED_ODER = "order";
        public static final String TRANSACTION_MAPPED_PAYMENT = "payment";
        public static final String TRANSACTION_MAPPED_STATUS = "status";
    }
}
