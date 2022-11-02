package uit.javabackend.webclonethecoffeehouse.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.validation.annotation.UniqueCode;
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

    @Length(min = 5, max = 50, message = "")
    @UniqueCode
    private String code;

    @Length(min = 5, max = 50, message = "")
    private String description;

    @Length(min = 5, max = 50, message = "")
    private Integer allowedUsers;

    @Length(min = 5, max = 50, message = "")
    private Integer limitAmountOnUser;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATETIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtils.DATETIME_FORMAT)
    private LocalDateTime effectiveDay;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATETIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtils.DATETIME_FORMAT)
    private LocalDateTime expirationDay;

    @Enumerated(value = EnumType.STRING)
    private Discount.AmountType amountType;
}
