package uit.javabackend.webclonethecoffeehouse.product.validation.validator;

import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.repository.ProductRepository;
import uit.javabackend.webclonethecoffeehouse.product.validation.annotation.UniqueProductName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName,String> {

    private String message;
    private ProductRepository repository;
    public UniqueProductNameValidator(ProductRepository productRepository){
        this.repository=productRepository;
    }
    @Override
    public void initialize(UniqueProductName constraintAnnotation) {
        message=constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Optional<Product> productOptional = repository.findByName(name);
        if(productOptional.isEmpty())
            return true;
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
