package com.saatvik.app.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleAuthenticationException(AuthenticationException exception) {
        log.error(exception.getMessage());
        RestErrorResponse re = new RestErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                "Authentication failed at controller advice");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }
}
