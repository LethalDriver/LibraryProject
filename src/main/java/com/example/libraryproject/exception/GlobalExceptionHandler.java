package com.example.libraryproject.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ExpiredJwtException.class, JwtException.class, TokenBlacklistedException.class})
    public ResponseEntity<ProblemDetail> handleJwtExceptions(Exception e) {
        log.error("JWT exception", e);
        return buildResponseEntity(HttpStatusCode.valueOf(401), e.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<ProblemDetail> handleAuthenticationExceptions(Exception e) {
        log.error("Authentication exception", e);
        return buildResponseEntity(HttpStatusCode.valueOf(403), e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        log.error("User already exists", e);
        return buildResponseEntity(HttpStatusCode.valueOf(409), e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Entity not found", e);
        return buildResponseEntity(HttpStatusCode.valueOf(404), e.getMessage());
    }

    @ExceptionHandler(NoBookInStockException.class)
    public ResponseEntity<ProblemDetail> handleNoBookInStockException(NoBookInStockException e) {
        log.error("No book in stock", e);
        return buildResponseEntity(HttpStatusCode.valueOf(404), e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> handleIllegalStateException(IllegalStateException e) {
        log.error("Illegal state", e);
        return buildResponseEntity(HttpStatusCode.valueOf(400), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Access denied", e);
        return buildResponseEntity(HttpStatusCode.valueOf(403), e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Constraint violation", e);
        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            message.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("\n");
        }
        return buildResponseEntity(HttpStatusCode.valueOf(400), message.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception e) {
        log.error("Unhandled exception", e);
        return buildResponseEntity(HttpStatusCode.valueOf(500), "Internal server error");
    }

    private ResponseEntity<ProblemDetail> buildResponseEntity(HttpStatusCode status, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        return new ResponseEntity<>(problemDetail, status);
    }
}