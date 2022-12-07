package uit.javabackend.webclonethecoffeehouse.security.authorization;

import uit.javabackend.webclonethecoffeehouse.role.model.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TCHOperation {
    String name() default "";

    Operation.Type type() default Operation.Type.FETCH;
}
