package uit.javabackend.webclonethecoffeehouse.business.validation.validator;

import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.model.UserDiscount;
import uit.javabackend.webclonethecoffeehouse.business.repository.DiscountRepository;
import uit.javabackend.webclonethecoffeehouse.business.repository.UserDiscountRepository;
import uit.javabackend.webclonethecoffeehouse.business.validation.annotation.UniqueCode;
import uit.javabackend.webclonethecoffeehouse.business.validation.annotation.UniqueUserDiscountDescription;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueUserDiscountDescriptionValidator implements ConstraintValidator<UniqueUserDiscountDescription, String> {
    private final UserDiscountRepository repository;
    private String message;

    public UniqueUserDiscountDescriptionValidator(UserDiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueUserDiscountDescription constraintAnnotation) {
        message = constraintAnnotation.message();
    }
    @Override
    public boolean isValid(String description, ConstraintValidatorContext context) {
        Optional<UserDiscount> userDiscountOptional = repository.findByDescription(description);
        if (userDiscountOptional.isEmpty())
            return true;
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
