package com.saatvik.app.exception;

import org.springframework.http.HttpStatus;

public record RestErrorResponse(int status, String message) {
}
