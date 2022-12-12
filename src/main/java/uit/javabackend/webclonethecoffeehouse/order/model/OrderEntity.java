package uit.javabackend.webclonethecoffeehouse.order.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderEntity {
    @UtilityClass
    public static class Order{
        public static final String TABLE_NAME ="TCH_ORDER";
        public static final String CUSTOMER_NAME ="P_CUSTOMER_NAME";
        public static final String ADDRESS ="P_ADDRESS";
        public static final String NOTE ="P_NOTE";
        public static final String CODE_COUPON ="P_CODE_COUPON";
        public static final String TOTAL_PRICE ="P_TOTALPRICE";
        public static final String ORDER_STATUS ="P_STATUS";
        public static final String USER_ID ="P_USER_ID";
        public static final String DISCOUNT_ID ="P_DISCOUNT_ID";
        public static final String PAYMENT_ID ="P_PAYMENT_ID";
        public static final String VNPAY_PAYMENT_ID ="P_VNPAYPAYMENT_ID";
    }

    @UtilityClass
    public static class OrderMapped {
        public static final String ORDER_MAPPED_ORDERPRODUCT ="order";
        public static final String ORDER_MAPPED_PAYMENT ="payment";
    }


}
