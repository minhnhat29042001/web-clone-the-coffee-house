package uit.javabackend.webclonethecoffeehouse.product.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductEntity {
    @UtilityClass
    public static class Product{
        public static final String TABLE_NAME ="TCH_PRODUCT";
        public static final String NAME ="P_NAME";
        public static final String PRICE ="P_PRICE";
        public static final String CURRENCY ="P_CURRENCY_ID";
        public static final String IMG_URL ="P_IMG";
        public static final String PRODUCTGROUP ="P_PRODUCTGROUP_ID";
        public static final String ORDERPRODUCT ="P_PRODUCTGROUP_ID";
        public static final String DESCRIPTION ="P_DESCRIPTION";
    }

    @UtilityClass
    public static class ProductMappedCurrency{
        public static final String CURRENCY_MAPPED_PRODUCT ="currency";
    }

    @UtilityClass
    public static class ProductMappedCollection{
        public static final String PRODUCTGROUP_MAPPED_PRODUCT ="productGroup";
    }

    @UtilityClass
    public static class ProductMappedOrderProduct{
        public static final String PRODUCT_MAPPED_ODERPRODUCT ="product";
    }
}

