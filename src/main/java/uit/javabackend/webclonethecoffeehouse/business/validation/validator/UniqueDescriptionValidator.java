package uit.javabackend.webclonethecoffeehouse.business.validation.validator;

import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.repository.DiscountRepository;
import uit.javabackend.webclonethecoffeehouse.business.validation.annotation.UniqueCode;
import uit.javabackend.webclonethecoffeehouse.business.validation.annotation.UniqueDescription;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueDescriptionValidator implements ConstraintValidator<UniqueDescription,String> {

    private final DiscountRepository repository;
    private String message;

    public UniqueDescriptionValidator(DiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueDescription constraintAnnotation) {
        message = constraintAnnotation.message();
    }
    @Override
    public boolean isValid(String description, ConstraintValidatorContext context) {
        Optional<Discount> discountOptional = repository.findByDescription(description);
        if (discountOptional.isEmpty())
            return true;
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
