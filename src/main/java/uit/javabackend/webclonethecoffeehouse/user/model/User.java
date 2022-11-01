package uit.javabackend.webclonethecoffeehouse.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = UserEntity.User.TABLE_NAME)
@NamedQueries({
        @NamedQuery(name = "User.findByUsernameLikeIgnoreCase", query = "select u from User u where upper(u.username) like upper(:username)")
})
public class User extends BaseEntity {
    @Column(name = UserEntity.User.USERNAME
            , nullable = false
            , unique = true
            , length = 100
            , updatable = false
    )
    private String username;

    @Column(name = UserEntity.User.PASSWORD, nullable = false)
    private String password;

    @Column(name = UserEntity.User.EMAIL
            , unique = true
            , nullable = false
            , length = 100
    )
    private String email;

    @Column(name = UserEntity.User.PHONE
            , nullable = false
            , length = 11
    )
    private String phone;

    @Column(name = UserEntity.User.BIRTH)
    private String birth;

    @Column(name = UserEntity.User.AVATAR)
    private String avatar;

    @Column(name = UserEntity.User.GENDER)
    @Enumerated(EnumType.STRING)
    private Gender gender;


    @ManyToMany(mappedBy = "users")
    private Set<UserGroup> userGroups = new LinkedHashSet<>();

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
}