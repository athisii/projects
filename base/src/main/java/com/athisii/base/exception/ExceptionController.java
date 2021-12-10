package com.athisii.base.exception;

import com.athisii.base.dto.ResponseDTO;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author athisii
 * @version 1.0
 * @since 12/10/21
 */

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionController.class);
    private final MessageSource messageSource;

    public ExceptionController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessageList = ex.getBindingResult().getAllErrors().stream().map((e) -> {
            if (e instanceof FieldError fieldError) {
                return messageSource.getMessage(fieldError, null);
            } else {
                return e.getDefaultMessage();
            }
        }).collect(Collectors.toList());

        ResponseDTO<String> exceptionResponse = new ResponseDTO<>(false,
                "Validation Failed: " + String.join(",", errorMessageList),
                request.getDescription(false));
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(GenericException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO handleGenericException(GenericException ex) {
        log.error("--> Generic error occurred {}", ex);
        return new ResponseDTO<>(false, ex.getMessage(), ex.getData());

    }
}
