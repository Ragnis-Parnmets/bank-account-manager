package com.example.bank_account_manager.infrastructure.rest;

import com.example.bank_account_manager.infrastructure.rest.exception.AccountAlreadyExistsException;
import com.example.bank_account_manager.infrastructure.rest.error.ApiError;
import com.example.bank_account_manager.infrastructure.rest.exception.DataNotFoundException;
import com.example.bank_account_manager.infrastructure.rest.exception.ForbiddenException;
import com.example.bank_account_manager.infrastructure.rest.exception.InsufficientFundsException;
import com.example.bank_account_manager.infrastructure.rest.exception.PositiveAmountException;
import com.example.bank_account_manager.infrastructure.rest.exception.SameAccountException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class watches for exceptions thrown by REST controllers
 * and turns them into friendly JSON error responses.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles cases where a user is not allowed to perform an action.
     * Produces a 403 Forbidden response with an error message and request path.
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbiddenException(ForbiddenException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.FORBIDDEN);
        apiError.setMessage(exception.getMessage());
        apiError.setPath(request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles cases where requested data cannot be found.
     * Produces a 404 Not Found response with an error message and request path.
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiError> handleDataNotFoundException(DataNotFoundException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setMessage(exception.getMessage());
        apiError.setPath(request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles cases where an account with the same account number already exists.
     * Produces a 409-Conflict response with a message and the request path.
     */
    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAccountAlreadyExists(AccountAlreadyExistsException ex,
                                                               HttpServletRequest request) {
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.CONFLICT);
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Handles business rule violations for transfers.
     * Produces a 400 Bad Request response.
     */
    @ExceptionHandler({InsufficientFundsException.class, PositiveAmountException.class, SameAccountException.class})
    public ResponseEntity<ApiError> handleTransferBusinessErrors(RuntimeException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setPath(request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation failures on method arguments.
     * Pulls out the first validation error, then returns a 400 Bad Request
     * response with a simple message and the request path.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {

        FieldError firstError = exception.getBindingResult()
                .getFieldErrors()
                .getFirst();

        ServletWebRequest servletReq = (ServletWebRequest) webRequest;
        String path = servletReq.getRequest().getRequestURI();

        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(firstError.getField() + ": " + firstError.getDefaultMessage());
        apiError.setPath(path);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
