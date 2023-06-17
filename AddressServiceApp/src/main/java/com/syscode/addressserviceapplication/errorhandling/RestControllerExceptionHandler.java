package com.syscode.addressserviceapplication.errorhandling;

import com.syscode.addressserviceapplication.errorhandling.exceptions.InvalidAuthHeaderException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

  @ExceptionHandler(InvalidAuthHeaderException.class)
  public ResponseEntity<ErrorMessage> handleInvalidAuthHeader(InvalidAuthHeaderException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(e.getMessage()));
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<ErrorMessage> handleJwtExceptions(JwtException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(e.getMessage()));
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<ErrorMessage> handleMissingRequestHeaders(MissingRequestHeaderException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(e.getMessage()));
  }

}
