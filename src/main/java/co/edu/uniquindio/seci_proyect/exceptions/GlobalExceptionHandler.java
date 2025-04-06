package co.edu.uniquindio.seci_proyect.exceptions;

import co.edu.uniquindio.seci_proyect.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValueConflictException.class)
    public ResponseEntity<ErrorResponse> handleValueConflictException(ValueConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("ERROR",ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleValueConflictException(MethodArgumentNotValidException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ex.getFieldErrors().stream().map(
                        e->new ErrorResponse("ERROR",e.getField()+"->"+e.getDefaultMessage())
                ).toList()
        );
    }
}
