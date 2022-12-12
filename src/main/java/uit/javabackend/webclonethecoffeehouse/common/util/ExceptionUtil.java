package uit.javabackend.webclonethecoffeehouse.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ExceptionUtil {
    public static final String DEFAULT_UNEXPECTED_MESSAGE = "Ops! Something wrong happens...";

    public List<String> getErrors(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
    }

    public static List<String> getErrors(RuntimeException exception) {
        return List.of(exception.getMessage());
    }

    public static List<String> getIOErrors(IOException exception) {
        return List.of(exception.getMessage());
    }

    public static List<String> getErrors(MethodArgumentNotValidException exception) {
        return exception.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
