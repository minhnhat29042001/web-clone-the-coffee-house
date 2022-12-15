package uit.javabackend.webclonethecoffeehouse.role.validation.validator;

import uit.javabackend.webclonethecoffeehouse.role.model.Operation;
import uit.javabackend.webclonethecoffeehouse.role.repository.OperationRepository;
import uit.javabackend.webclonethecoffeehouse.role.validation.annotation.UniqueOperationCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueOperationCodeValidator
        implements ConstraintValidator<UniqueOperationCode, String> {
    private final OperationRepository repository;
    private String message;

    public UniqueOperationCodeValidator(OperationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueOperationCode constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        Optional<Operation> operationOpt = repository.findByCode(code);

        if (operationOpt.isEmpty())
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
