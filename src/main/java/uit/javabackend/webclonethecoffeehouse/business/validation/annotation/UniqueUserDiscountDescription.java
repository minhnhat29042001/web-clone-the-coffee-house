package uit.javabackend.webclonethecoffeehouse.business.validation.annotation;

import uit.javabackend.webclonethecoffeehouse.business.validation.validator.UniqueCodeValidator;
import uit.javabackend.webclonethecoffeehouse.business.validation.validator.UniqueUserDiscountDescriptionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUserDiscountDescriptionValidator.class)
@Target(ElementType.FIELD)// dùng vô mảng nào
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserDiscountDescription {
    String message() default "{userdiscount.code.existed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
