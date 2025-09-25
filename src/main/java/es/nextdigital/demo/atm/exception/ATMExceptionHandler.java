package es.nextdigital.demo.atm.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ATMExceptionHandler {

  @ExceptionHandler(CardNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleCardNotFoundException(
      CardNotFoundException ex, WebRequest request) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
  }

  @ExceptionHandler(ATMNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleATMNotFoundException(
      ATMNotFoundException ex, WebRequest request) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
  }

  @ExceptionHandler(InsufficientFundsException.class)
  public ResponseEntity<Map<String, Object>> handleInsufficientFundsException(
      InsufficientFundsException ex, WebRequest request) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
      IllegalArgumentException ex, WebRequest request) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  private ResponseEntity<Map<String, Object>> buildErrorResponse(
      HttpStatus status, String message, WebRequest request) {
    Map<String, Object> errorDetails = new HashMap<>();
    errorDetails.put("timestamp", LocalDateTime.now());
    errorDetails.put("message", message);
    errorDetails.put("details", request.getDescription(false));
    errorDetails.put("status", status.value());

    return new ResponseEntity<>(errorDetails, status);
  }
}