package uit.javabackend.webclonethecoffeehouse.user.validation.validator;

import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.repository.UserRepository;
import uit.javabackend.webclonethecoffeehouse.user.validation.annotation.CorrectGender;
import uit.javabackend.webclonethecoffeehouse.user.validation.annotation.UniqueEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CorrectGenderValidator implements ConstraintValidator<CorrectGender, String> {

    private String message;

    @Override
    public void initialize(CorrectGender constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext context) {
        if (gender == null) return true;
        for (User.Gender g : User.Gender.values()) {
            if (g.name().equals(gender)) {
                return true;
            }
        }
        return false;
    }
}
