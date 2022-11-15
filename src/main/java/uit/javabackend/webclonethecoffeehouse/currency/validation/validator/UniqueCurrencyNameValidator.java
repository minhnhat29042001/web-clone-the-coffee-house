package uit.javabackend.webclonethecoffeehouse.currency.validation.validator;

import uit.javabackend.webclonethecoffeehouse.currency.model.Currency;
import uit.javabackend.webclonethecoffeehouse.currency.repository.CurrencyRepository;
import uit.javabackend.webclonethecoffeehouse.currency.validation.annotation.UniqueCurrencyName;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.repository.ProductRepository;
import uit.javabackend.webclonethecoffeehouse.product.validation.annotation.UniqueProductName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueCurrencyNameValidator implements ConstraintValidator<UniqueCurrencyName,String> {


    private String message;
    private final CurrencyRepository repository;
    public UniqueCurrencyNameValidator(CurrencyRepository currencyRepository){
        this.repository=currencyRepository;
    }

    @Override
    public void initialize(UniqueCurrencyName constraintAnnotation) {
        message=constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Optional<Currency> currencyOptional = repository.findByName(name);
        if(currencyOptional.isEmpty())
            return true;
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
