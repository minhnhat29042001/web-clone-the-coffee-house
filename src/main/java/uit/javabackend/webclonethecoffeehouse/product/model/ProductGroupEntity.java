package uit.javabackend.webclonethecoffeehouse.product.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductGroupEntity {
    @UtilityClass
    public static class ProductGroup{
        public static final String TABLE_NAME ="TCH_PRODUCTGROUP";
        public static final String NAME ="TCH_PRODUCTGROUP_NAME";
    }

    @UtilityClass
    public static class ProductGroupMappedProduct{
        public static final String COLLECTION_MAPPED_PRODUCT ="productGroup";
    }
}
