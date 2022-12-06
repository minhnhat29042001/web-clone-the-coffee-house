package uit.javabackend.webclonethecoffeehouse.user.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserEntity {
    @UtilityClass
    public class User {
        public static final String TABLE_NAME = "TCH_USER";
        public static final String USERNAME = "TCH_USERNAME";
        public static final String PASSWORD = "TCH_PASSWORD";
        public static final String EMAIL = "TCH_EMAIL";
        public static final String PHONE = "TCH_PHONE";
        public static final String GENDER = "TCH_GENDER";
        public static final String AVATAR = "TCH_AVATAR";
        public static final String BIRTH = "TCH_BIRTH";
    }

    @UtilityClass
    public class UserMapped {
        public static final String USER_MAPPED_ORDER = "user";
    }
}
