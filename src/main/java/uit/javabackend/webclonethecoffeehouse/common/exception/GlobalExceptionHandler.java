package uit.javabackend.webclonethecoffeehouse.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uit.javabackend.webclonethecoffeehouse.common.model.ResponseDTO;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice // ngóng bắt lỗi và trả về json
public class GlobalExceptionHandler { // bắt các lỗi

    @ExceptionHandler(ConstraintViolationException.class) // bắt kiểu lỗi
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleConstraintViolationException(ConstraintViolationException exception) { // hàm bắt lỗi ConstraintViolation
            return ResponseUtil.error(exception,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ){
        return ResponseUtil.error(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TCHBusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleLoginException(
            TCHBusinessException exception
    ) {
        return ResponseUtil.error(exception, HttpStatus.BAD_REQUEST);
    }
}
