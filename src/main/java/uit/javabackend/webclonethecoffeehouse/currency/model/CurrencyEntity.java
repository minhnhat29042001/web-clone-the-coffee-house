package uit.javabackend.webclonethecoffeehouse.currency.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CurrencyEntity {
    @UtilityClass
    public static class Currency{
        public static final String TABLE_NAME ="TCH_CURRENCY";
        public static final String NAME ="TCH_CURRENCY_NAME";
    }

    @UtilityClass
    public static class CurrencyMappedProduct{
        public static final String PRODUCT_MAPPED_CURRENCY ="currency";
    }
}
