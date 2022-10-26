package uit.javabackend.webclonethecoffeehouse.product.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductEntity {
    @UtilityClass
    public static class Product{
        public static final String TABLE_NAME ="TCH_PRODUCT";
        public static final String PRODUCT_NAME ="P_NAME";
        public static final String PRODUCT_URL ="P_URL";
        public static final String PRODUCT_PRICE ="P_PRICE";
        public static final String PRODUCT_CURRENCY ="P_CURRENCY";
        public static final String PRODUCT_IMG_URL ="P_IMG";
        public static final String PRODUCT_COLLECTION ="P_COLLECTION_ID";
        public static final String PRODUCT_DESCRIPTION ="P_DESCRIPTION";
    }

    @UtilityClass
    public static class ProductMappedCurrency{
        public static final String CURRENCY_MAPPED_PRODUCT ="currency";
    }

    @UtilityClass
    public static class ProductMappedCollection{
        public static final String PRODUCTGROUP_MAPPED_PRODUCT ="productGroup";
    }
}
