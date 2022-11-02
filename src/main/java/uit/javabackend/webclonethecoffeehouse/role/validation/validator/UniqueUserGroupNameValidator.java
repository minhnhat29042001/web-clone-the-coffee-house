package uit.javabackend.webclonethecoffeehouse.role.validation.validator;

import uit.javabackend.webclonethecoffeehouse.role.model.Role;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;
import uit.javabackend.webclonethecoffeehouse.role.repository.RoleRepository;
import uit.javabackend.webclonethecoffeehouse.role.repository.UserGroupRepository;
import uit.javabackend.webclonethecoffeehouse.role.validation.annotation.UniqueRoleName;
import uit.javabackend.webclonethecoffeehouse.role.validation.annotation.UniqueUserGroupName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueUserGroupNameValidator
        implements ConstraintValidator<UniqueUserGroupName, String> {
    private final UserGroupRepository repository;
    private String message;

    public UniqueUserGroupNameValidator(UserGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueUserGroupName constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Optional<UserGroup> userGroup = repository.findByName(name);

        if (userGroup.isEmpty())
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
