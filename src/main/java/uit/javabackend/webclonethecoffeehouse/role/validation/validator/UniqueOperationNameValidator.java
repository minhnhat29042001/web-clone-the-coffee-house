package uit.javabackend.webclonethecoffeehouse.role.validation.validator;

import uit.javabackend.webclonethecoffeehouse.role.model.Operation;
import uit.javabackend.webclonethecoffeehouse.role.repository.OperationRepository;
import uit.javabackend.webclonethecoffeehouse.role.validation.annotation.UniqueOperationName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueOperationNameValidator
        implements ConstraintValidator<UniqueOperationName, String> {
    private final OperationRepository repository;
    private String message;

    public UniqueOperationNameValidator(OperationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueOperationName constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Optional<Operation> operationOpt = repository.findByName(name);

        if (operationOpt.isEmpty())
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
