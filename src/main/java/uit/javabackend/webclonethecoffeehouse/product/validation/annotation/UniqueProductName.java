package uit.javabackend.webclonethecoffeehouse.product.validation.annotation;

import uit.javabackend.webclonethecoffeehouse.product.validation.validator.UniqueProductNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueProductNameValidator.class)
@Target(ElementType.FIELD)// dùng vô mảng nào
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueProductName {
    String message() default "{role.name.existed}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
