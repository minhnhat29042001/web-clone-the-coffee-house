package uit.javabackend.webclonethecoffeehouse.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;
import uit.javabackend.webclonethecoffeehouse.order.model.OrderEntity;
import uit.javabackend.webclonethecoffeehouse.order.model.OrderProduct;
import uit.javabackend.webclonethecoffeehouse.role.model.RoleEntity;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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

    @Column(name = UserEntity.User.PASSWORD)
    private String password;

    @Column(name = UserEntity.User.EMAIL
            , unique = true
            , nullable = false
            , length = 100
    )
    private String email;

    @Column(name = UserEntity.User.PHONE)
    private String phone;

    @Column(name = UserEntity.User.BIRTH)
    private String birth;

    @Column(name = UserEntity.User.AVATAR)
    private String avatar;

    @Column(name = UserEntity.User.GENDER)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // OneToMany
    // relationship Bidirectional
    @OneToMany(mappedBy =UserEntity.UserMapped.USER_MAPPED_ORDER)
    private List<Order> orders = new ArrayList<>();

    public User addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
        return this;
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
        order.setUser(null);
    }

    @ManyToMany(mappedBy = RoleEntity.UserGroupMappedUser.USER_MAPPED_USER_GROUP)
    private Set<UserGroup> userGroups = new LinkedHashSet<>();

    @Transient
    private String token;
    @Enumerated(EnumType.STRING)
    private Provider provider;

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    public enum Provider {
        local, google
    }
}