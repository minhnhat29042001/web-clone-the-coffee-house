package uit.javabackend.webclonethecoffeehouse.business.validation.validator;

import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.repository.DiscountRepository;
import uit.javabackend.webclonethecoffeehouse.business.validation.annotation.UniqueCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueCodeValidator implements ConstraintValidator<UniqueCode, String> {

    private final DiscountRepository repository;
    private String message;

    public UniqueCodeValidator(DiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueCode constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        Optional<Discount> userOptional = repository.findByCode(code);
        if (userOptional.isEmpty())
            return true;
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
