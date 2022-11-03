package uit.javabackend.webclonethecoffeehouse.business.validation.annotation;

import uit.javabackend.webclonethecoffeehouse.business.validation.validator.UniqueCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueCodeValidator.class)
@Target(ElementType.FIELD)// dùng vô mảng nào
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCode {
    String message() default "{discount.code.existed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
