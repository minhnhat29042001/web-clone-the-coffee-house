package uit.javabackend.webclonethecoffeehouse.order.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderEntity {
    @UtilityClass
    public static class Order{
        public static final String TABLE_NAME ="TCH_ORDER";
        public static final String NAME ="P_NAME";
        public static final String ADDRESS ="P_ADDRESS";
        public static final String NOTE ="P_NOTE";
        public static final String USE_COUPON ="P_USECOUPON";
        public static final String TOTAL_PRICE ="P_TOTALPRICE";
        public static final String USER ="P_USER_ID";
    }

    @UtilityClass
    public static class OrderMappedOrderProduct {
        public static final String ORDER_MAPPED_ORDERPRODUCT ="order";
    }
}
