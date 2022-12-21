package uit.javabackend.webclonethecoffeehouse.business.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BusinessEntity {

    @UtilityClass
    public static class Business {
        public static final String TABLE_NAME = "TCH_BUSINESS";
        public static final String COMPANY_NAME = "COMPANY_NAME";
        public static final String REPRESENTATIVE_NAME = "REPRESENTATIVE_NAME";
        public static final String BRAND = "BRAND";
        public static final String ORDER_HOTLINE = "ORDER_HOTLINE";
        public static final String RECRUITMENT_HOTLINE = "RECRUITMENT_HOTLINE";
        public static final String EMAIL = "EMAIL";
        public static final String ADDRESS = "ADDRESS";
        public static final String TAX_CODE = "TAX_CODE";
        public static final String SOCIAL_NETWORK_URL = "SOCIAL_NETWORK_URL";
        public static final String WEBSITE_URL = "WEBSITE_URL";
        public static final String IMAGE_URL = "IMAGE_URL";
    }

    @UtilityClass
    public static class Discount {
        public static final String TABLE_NAME = "TCH_DISCOUNT";
        public static final String CODE = "CODE";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String IMAGEURL = "IMAGEURL";
        public static final String ALLOWED_USERS = "ALLOWED_USERS";
        public static final String LIMIT_AMOUNT_ON_USERS = "LIMIT_AMOUNT_ON_USERS";
        public static final String EFFECTIVE_DAY = "EFFECTIVE_DAY";
        public static final String EXPIRATION_DAY = "EXPIRATION_DAY";
        public static final String AMOUNT_TYPE = "AMOUNT_TYPE";
        public static final String DISCOUNT_AMOUNT ="DISCOUNT_AMOUNT";

    }

    @UtilityClass
    public static class DiscountMapped {
        public static final String DISCOUNT_MAPPED_USERDISCOUNT = "discount";

    }

    @UtilityClass
    public static class UserDiscount {
        public static final String TABLE_NAME = "TCH_USERDISCOUNT";

        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String USED_COUNT = "USED_COUNT";
        public static final String USER_ID = "USER_ID";
        public static final String DISCOUNT_ID = "DISCOUNT_ID";


    }
}
