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
    public static class RoleMappedUserGroup {
        public static final String USER_GROUP_MAPPED_ROLE = "userGroups";
        public static final String JOIN_TABLE = "TCH_ROLE_USER_GROUP";
        public static final String JOIN_TABLE_ROLE_ID = "TCH_ROLE_ID";
        public static final String JOIN_TABLE_USER_GROUP_ID = "TCH_USER_GROUP_ID";
    }

    @UtilityClass
    public static class UserGroupMappedUser {
        public static final String USER_MAPPED_USER_GROUP = "users";
        public static final String JOIN_TABLE = "TCH_USER_USER_GROUP";
        public static final String JOIN_TABLE_USER_ID = "TCH_USER_ID";
        public static final String JOIN_TABLE_USER_GROUP_ID = "TCH_USER_GROUP_ID";
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

        public static final String TABLE_NAME = "TCH_USER_GROUP";
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
