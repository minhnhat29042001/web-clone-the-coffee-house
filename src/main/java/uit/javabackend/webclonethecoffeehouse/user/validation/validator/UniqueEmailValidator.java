package uit.javabackend.webclonethecoffeehouse.user.validation.validator;

import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.repository.UserRepository;
import uit.javabackend.webclonethecoffeehouse.user.validation.annotation.UniqueEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository repository;
    private String message;

    public UniqueEmailValidator(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override

    public boolean isValid(String email, ConstraintValidatorContext context) {
        Optional<User> userOptional = repository.findByEmail(email);

        if (userOptional.isEmpty())
            return true;
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
