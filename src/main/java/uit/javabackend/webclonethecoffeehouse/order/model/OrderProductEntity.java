package uit.javabackend.webclonethecoffeehouse.order.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderProductEntity {
    @UtilityClass
    public static class OrderProduct{
        public static final String TABLE_NAME ="TCH_ORDERPRODUCT";
        public static final String TOTALPRICE ="P_TOTALPRICE";
        public static final String NOTE ="P_NOTE";
        public static final String QUANTITY = "P_QUANTITY";
        public static final String PRODUCT_ID ="P_PRODUCT_ID";
        public static final String ORDER_ID ="P_ORDER_ID";
    }

}
