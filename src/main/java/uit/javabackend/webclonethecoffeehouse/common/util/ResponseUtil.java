package uit.javabackend.webclonethecoffeehouse.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import uit.javabackend.webclonethecoffeehouse.common.model.ResponseDTO;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Collections;

@UtilityClass
public class ResponseUtil {
    public static ResponseEntity<ResponseDTO> get(Object result, HttpStatus status){
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .content(result)
                        .hasErrors(false)
                        .errors(Collections.emptyList())
                        .timestamp(DateTimeUtils.now())
                        .status(status.value())
                        .build()
                , status
        );
    }

    public static ResponseEntity<ResponseDTO> error(ConstraintViolationException exception, HttpStatus status){
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .content(null)
                        .hasErrors(true)
                        .errors(ExceptionUtil.getErrors(exception))
                        .timestamp(DateTimeUtils.now())
                        .status(status.value())
                        .build()
                , status
        );
    }

    public static ResponseEntity<ResponseDTO> error(RuntimeException exception, HttpStatus status){
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .content(null)
                        .hasErrors(true)
                        .errors(ExceptionUtil.getErrors(exception))
                        .timestamp(DateTimeUtils.now())
                        .status(status.value())
                        .build()
                , status
        );
    }

    public static ResponseEntity<ResponseDTO> errorSaveFile(IOException exception, HttpStatus status){
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .content(null)
                        .hasErrors(true)
                        .errors(ExceptionUtil.getIOErrors(exception))
                        .timestamp(DateTimeUtils.now())
                        .status(status.value())
                        .build()
                , status
        );
    }

    public static ResponseEntity<ResponseDTO> error(MethodArgumentNotValidException exception, HttpStatus status) {
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .content(null)
                        .hasErrors(true)
                        .errors(ExceptionUtil.getErrors(exception))
                        .timestamp(DateTimeUtils.now())
                        .status(status.value())
                        .build()
                , status
        );
    }
}
