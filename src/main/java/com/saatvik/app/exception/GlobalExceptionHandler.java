package com.saatvik.app.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import javax.naming.AuthenticationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<RestErrorResponse> handleExpiredJwtException(ExpiredJwtException exception){
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), "Token Expired"));
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<RestErrorResponse> handleAllException(NoResourceFoundException exception){
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RestErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        log.error(exception.getMessage());
        RestErrorResponse re = new RestErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Please check your username or password is correct!!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseBody
    public ProblemDetail handleInsufficientAuthenticationException(InsufficientAuthenticationException exception) {
        log.error(exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,"You don't have permission to access this resource!");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ProblemDetail handleAuthenticationException(AuthenticationException exception) {
        log.error(exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }
}
