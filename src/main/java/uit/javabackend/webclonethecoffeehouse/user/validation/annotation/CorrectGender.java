package uit.javabackend.webclonethecoffeehouse.user.validation.annotation;

import uit.javabackend.webclonethecoffeehouse.user.validation.validator.CorrectGenderValidator;
import uit.javabackend.webclonethecoffeehouse.user.validation.validator.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CorrectGenderValidator.class)
@Target(ElementType.FIELD)// dùng vô mảng nào
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrectGender {
    String message() default "{user.gender.incorrect}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
