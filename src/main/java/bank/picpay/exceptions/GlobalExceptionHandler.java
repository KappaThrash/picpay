package bank.picpay.exceptions;

import bank.picpay.exceptions.custom_exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(
                new ErrorResponse(
                        OffsetDateTime.now(),
                        HttpStatus.UNPROCESSABLE_CONTENT.value(),
                        "IllegalArgumentException",
                        ex.getMessage(),
                        request.getRequestURI()
                )
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponse(
                        OffsetDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        "DataIntegrityViolationException",
                        ex.getMessage(),
                        request.getRequestURI()
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(
                new ErrorResponse(
                        OffsetDateTime.now(),
                        HttpStatus.UNPROCESSABLE_CONTENT.value(),
                        "MethodArgumentNotValidException",
                        ex.getLocalizedMessage(),
                        request.getRequestURI()
                )
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        OffsetDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "UserNotFoundException",
                        ex.getMessage(),
                        request.getRequestURI()
                )
        );
    }
}
