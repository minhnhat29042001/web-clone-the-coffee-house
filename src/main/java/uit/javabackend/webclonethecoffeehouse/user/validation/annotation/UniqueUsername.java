package uit.javabackend.webclonethecoffeehouse.user.validation.annotation;

import uit.javabackend.webclonethecoffeehouse.user.validation.validator.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target(ElementType.FIELD)// dùng vô mảng nào
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "{user.name.existed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
