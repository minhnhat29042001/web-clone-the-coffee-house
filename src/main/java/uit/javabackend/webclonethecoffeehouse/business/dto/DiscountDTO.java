package uit.javabackend.webclonethecoffeehouse.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.validation.annotation.UniqueCode;
import uit.javabackend.webclonethecoffeehouse.business.validation.annotation.UniqueDescription;
import uit.javabackend.webclonethecoffeehouse.common.util.DateTimeUtils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDTO implements Serializable {

    private UUID id;

    @Length(min = 5, max = 50, message = "Discount code must have length between {min} and {max}")
    private String code;

    @Length(min = 5, max = 50, message = "Discount description must have length between {min} and {max}")
    private String description;

    private String imageUrl;

    @Range(min = 1, message = "so luong nguoi dung it nhat la {min}")
    private Integer  numbersOfUsers;

    @Range(min = 1, message = "so luong su dung cho moi nguoi dung it nhat la {min}")
    private Integer limitAmountOnUser;

    private LocalDateTime effectiveDay;

    private LocalDateTime expirationDay;

    @Enumerated(value = EnumType.STRING)
    private Discount.AmountType amountType;

    @Range(min = 1000, message = "so tien giam cho nguoi dung it nhat la {min}")
    private Integer discountAmount;

    @Range(min = 1000, message = "so tien giam cho nguoi dung it nhat la {min}")
    private Integer minimumPriceOnOrder;

}
