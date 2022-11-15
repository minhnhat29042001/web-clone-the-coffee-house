package uit.javabackend.webclonethecoffeehouse.business.validation.annotation;

import uit.javabackend.webclonethecoffeehouse.business.validation.validator.UniqueCodeValidator;
import uit.javabackend.webclonethecoffeehouse.business.validation.validator.UniqueDescriptionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueDescriptionValidator.class)
@Target(ElementType.FIELD)// dùng vô mảng nào
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueDescription {
    String message() default "{discount.description.existed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
