package uit.javabackend.webclonethecoffeehouse.business.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.common.util.DateTimeUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Length(min = 5, max = 50, message = "")
    private String code;

    @Column(name = BusinessEntity.Discount.DESCRIPTION)
    @Length(min = 5, max = 50, message = "")
    private String description;

    @Column(name = BusinessEntity.Discount.ALLOWED_USERS)
    @Length(min = 5, max = 50, message = "")
    private Integer allowedUsers;

    @Column(name = BusinessEntity.Discount.LIMIT_AMOUNT_ON_USERS)
    @Length(min = 5, max = 50, message = "")
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

    public enum AmountType {
        AMOUNT_OFF, PERCENTAGE
    }
}
