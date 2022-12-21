package uit.javabackend.webclonethecoffeehouse.business.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.common.util.DateTimeUtils;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * them chu thich properties vao day hoac them tai lieu
 */

@Entity
@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@Table(name = BusinessEntity.Discount.TABLE_NAME)
public class Discount extends BaseEntity {

    @Column(name = BusinessEntity.Discount.CODE)
    @Length(min = 5, max = 50, message = "Discount code must have length between {min} and {max}")
    private String code;

    @Column(name = BusinessEntity.Discount.DESCRIPTION)
    @Length(min = 5, max = 50, message = "Discount description must have length between {min} and {max}")
    private String description;

    @Column(name = BusinessEntity.Discount.IMAGEURL)
    private String imageUrl;

    @Column(name = BusinessEntity.Discount.ALLOWED_USERS)
    private Integer numbersOfUsers;

    @Column(name = BusinessEntity.Discount.LIMIT_AMOUNT_ON_USERS)
    private Integer limitAmountOnUser;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATETIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtils.DATETIME_FORMAT)
    @Column(name = BusinessEntity.Discount.EFFECTIVE_DAY)
    private LocalDateTime effectiveDay;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATETIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtils.DATETIME_FORMAT)
    @Column(name = BusinessEntity.Discount.EXPIRATION_DAY)
    private LocalDateTime expirationDay;

    @Column(name = BusinessEntity.Discount.AMOUNT_TYPE)
    @Enumerated(value = EnumType.STRING)
    private AmountType amountType;

    @Column(name = BusinessEntity.Discount.DISCOUNT_AMOUNT)
    @Range(min = 1, message= "Discount amount must have lowest range which is {min}")
    private Integer discountAmount;


    //OneToMany
    //relationship - bidirectional

    @OneToMany(mappedBy = BusinessEntity.DiscountMapped.DISCOUNT_MAPPED_USERDISCOUNT)
    private List<Order> orders = new ArrayList<>();

    public Discount addOrder(Order order) {
        orders.add(order);
        order.setDiscount(this);
        return this;
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setDiscount(null);
    }


    @OneToMany(mappedBy = BusinessEntity.DiscountMapped.DISCOUNT_MAPPED_USERDISCOUNT)
    private Set<UserDiscount> userDiscounts = new LinkedHashSet<>();

    public Discount addUserDiscount(UserDiscount userDiscount) {
        this.userDiscounts.add(userDiscount);
        userDiscount.setDiscount(this);
        return this;
    }

    public void removeUserDiscount(UserDiscount userDiscount) {
        this.userDiscounts.remove(userDiscount);
        userDiscount.setDiscount(null);
    }

    public enum AmountType {
        MONEY, PERCENTAGE
    }
}
