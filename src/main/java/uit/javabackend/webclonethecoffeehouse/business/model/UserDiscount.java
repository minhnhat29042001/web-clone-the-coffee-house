package uit.javabackend.webclonethecoffeehouse.business.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.user.model.User;

import javax.persistence.*;

@Entity
@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@Table(name = BusinessEntity.UserDiscount.TABLE_NAME)
public class UserDiscount extends BaseEntity {

    @Column(name = BusinessEntity.UserDiscount.DESCRIPTION)
    @Length(min = 5, max = 50, message = "UserDiscount description must have length between {min} and {max}")
    private String description;

    @Column(name = BusinessEntity.UserDiscount.USED_COUNT)
    private Integer usedCount;

    @ManyToOne
    @JoinColumn(name = BusinessEntity.UserDiscount.USER_ID)
    private User user;

    @ManyToOne
    @JoinColumn(name = BusinessEntity.UserDiscount.DISCOUNT_ID)
    private Discount discount;
}
