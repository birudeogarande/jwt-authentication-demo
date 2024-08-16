package com.saatvik.app.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<RestErrorResponse> handleExpiredJwtException(ExpiredJwtException expiredJwtException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), expiredJwtException.getMessage()));
    }


    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleAuthenticationException(Exception ex) {

        RestErrorResponse re = new RestErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                "Authentication failed at controller advice");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }
}
