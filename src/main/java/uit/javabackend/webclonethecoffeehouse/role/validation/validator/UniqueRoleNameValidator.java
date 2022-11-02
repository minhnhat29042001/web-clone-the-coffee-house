package uit.javabackend.webclonethecoffeehouse.role.validation.validator;

import uit.javabackend.webclonethecoffeehouse.role.model.Role;
import uit.javabackend.webclonethecoffeehouse.role.repository.RoleRepository;
import uit.javabackend.webclonethecoffeehouse.role.validation.annotation.UniqueRoleName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueRoleNameValidator
        implements ConstraintValidator<UniqueRoleName, String> {
    private final RoleRepository repository;
    private String message;

    public UniqueRoleNameValidator(RoleRepository roleRepository) {
        this.repository = roleRepository;
    }

    @Override
    public void initialize(UniqueRoleName constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Optional<Role> roleOpt = repository.findByName(name);

        if (roleOpt.isEmpty())
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
