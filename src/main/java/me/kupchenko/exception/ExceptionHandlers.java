package me.kupchenko.exception;

import me.kupchenko.model.ResponseMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler({BusinessLogicException.class})
    public ResponseEntity<ResponseMetadata> handleBusinessLogicException(BusinessLogicException businessLogicException) {
        return ResponseEntity.badRequest()
                .body(new ResponseMetadata("business.logic.exception", businessLogicException.getMessage()));
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ResponseMetadata> defaultHandler(Throwable exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseMetadata("unknown.exception", "Something went wrong."));
    }
}
