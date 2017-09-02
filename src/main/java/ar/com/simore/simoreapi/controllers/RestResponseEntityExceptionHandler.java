package ar.com.simore.simoreapi.controllers;

import ar.com.simore.simoreapi.exceptions.RolesNotPresentException;
import ar.com.simore.simoreapi.exceptions.TreatmentTemplateNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global controller to handle exceptions
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /** Here we should handle all business exceptions we create. It returns BAD_REQUEST
     * @param ex Chequed exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = {TreatmentTemplateNotFoundException.class, RolesNotPresentException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /** Here we should handle all unhandled exceptions . It returns INTERNAL_SERVER_ERROR
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleConflictGneralException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getLocalizedMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}