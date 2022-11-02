package uit.javabackend.webclonethecoffeehouse.role.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleEntity {
    @UtilityClass
    public static class RoleMappedOperation {
        public static final String OPERATION_MAPPED_ROLE = "operations";
        public static final String JOIN_TABLE = "TCH_ROLE_OPERATION";
        public static final String JOIN_TABLE_ROLE_ID = "TCH_ROLE_ID";
        public static final String JOIN_TABLE_SERVICE_ID = "TCH_OPERATION_ID";
    }

    @UtilityClass
    public static class Role {
        public static final String TABLE_NAME = "TCH_ROLE";
        public static final String NAME = "TCH_NAME";
        public static final String DESCRIPTION = "TCH_DESCRIPTION";
        public static final String CODE = "TCH_CODE";
    }

    @UtilityClass
    public static class UserGroup {
        public static final String TABLE_NAME = "TCH_GROUP";
        public static final String NAME = "TCH_NAME";
        public static final String DESCRIPTION = "TCH_DESCRIPTION";
        public static final String CODE = "TCH_CODE";
    }

    @UtilityClass
    public static class Operation {
        public static final String TABLE_NAME = "TCH_OPERATION";
        public static final String NAME = "TCH_NAME";
        public static final String DESCRIPTION = "TCH_DESCRIPTION";
        public static final String CODE = "TCH_CODE";
        public static final String TYPE = "TCH_TYPE";
    }
}
