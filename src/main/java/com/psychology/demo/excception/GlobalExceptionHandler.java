package com.psychology.demo.excception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import com.psychology.demo.dto.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Giriş qadağandır: Token tapılmadı və ya səhvdir.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sizin bu əməliyyat üçün səlahiyyətiniz yoxdur.");
    }

    @Hidden
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFound(UsernameNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }




    @Hidden
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Hidden
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException ex, HttpServletRequest request) {
        String title = "Məlumat Xətası";
        if(ex.getStatusCode().equals(HttpStatus.CONFLICT)) {
            title="bu email artiq movcuddur(konflikt yaranir)";
        }
        return new ResponseEntity<>(
                new ErrorResponse(
                        LocalDateTime.now(),
                        ex.getStatusCode().value(),
                        title,
                        ex.getReason(),
                        request.getRequestURI()
                ),
                ex.getStatusCode()
        );
    }

    @Hidden
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Sistemdə gözlənilməz xəta baş verdi",
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Resurs Tapılmadı",
                        ex.getMessage(),
                        request.getRequestURI()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLogic(BusinessLogicException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        "Biznes Məntiqi Xətası",
                        ex.getMessage(),
                        request.getRequestURI()
                ),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler({
            DisabledException.class,
            AccountExpiredException.class,
            LockedException.class,
            BadCredentialsException.class,
            CredentialsExpiredException.class
    })
    public ResponseEntity<ErrorResponse> handleAuthenticationExceptions(RuntimeException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.UNAUTHORIZED.value(), // 401 Unauthorized
                        "Autentifikasiya Xətası",
                        ex.getMessage(),
                        request.getRequestURI()
                ),
                HttpStatus.UNAUTHORIZED
        );
    }



}