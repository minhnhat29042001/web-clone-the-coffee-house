package uit.javabackend.webclonethecoffeehouse.currency.validation.annotation;

import uit.javabackend.webclonethecoffeehouse.currency.validation.validator.UniqueCurrencyNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueCurrencyNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCurrencyName {
    String message() default "{currency.name.existed}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
