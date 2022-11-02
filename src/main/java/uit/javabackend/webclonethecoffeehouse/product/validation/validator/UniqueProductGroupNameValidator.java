package uit.javabackend.webclonethecoffeehouse.product.validation.validator;

import uit.javabackend.webclonethecoffeehouse.product.model.ProductGroup;
import uit.javabackend.webclonethecoffeehouse.product.repository.ProductGroupRepository;
import uit.javabackend.webclonethecoffeehouse.product.validation.annotation.UniqueProductGroupName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueProductGroupNameValidator implements ConstraintValidator<UniqueProductGroupName,String> {

    private String message;
    private ProductGroupRepository repository;
    public UniqueProductGroupNameValidator(ProductGroupRepository productGroupRepository){
        this.repository= productGroupRepository;
    }

    @Override
    public void initialize(UniqueProductGroupName constraintAnnotation) {
        message=constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Optional<ProductGroup> collectionOptional = repository.findByName(name);
        if(collectionOptional.isEmpty())
            return true;
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
