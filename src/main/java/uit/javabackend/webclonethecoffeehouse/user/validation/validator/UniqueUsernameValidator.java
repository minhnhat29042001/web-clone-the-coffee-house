package uit.javabackend.webclonethecoffeehouse.user.validation.validator;

import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.repository.UserRepository;
import uit.javabackend.webclonethecoffeehouse.user.validation.annotation.UniqueUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository repository;
    private String message;

    public UniqueUsernameValidator(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Optional<User> userOptional = repository.findByName(name);
        if (userOptional.isEmpty())
            return true;
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
