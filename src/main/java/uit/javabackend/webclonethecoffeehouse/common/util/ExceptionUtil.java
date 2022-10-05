package uit.javabackend.webclonethecoffeehouse.common.util;

import lombok.experimental.UtilityClass;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ExceptionUtil {
    public static final String DEFAULT_UNEXPECTED_MESSAGE ="Ops! Something wrong happens...";

    public List<String> getErrors(ConstraintViolationException exception){
            return  exception.getConstraintViolations().stream().map(violation -> violation.getMessage()).collect(Collectors.toList());
    }

    public static List<String> getErrors(RuntimeException exception) {
        return List.of(DEFAULT_UNEXPECTED_MESSAGE);
    }
}
