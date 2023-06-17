package com.syscode.addressserviceapplication.errorhandling;

import com.syscode.addressserviceapplication.errorhandling.exceptions.InvalidAuthHeaderException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestControllerExceptionHandlerTest {

  @Spy
  private RestControllerExceptionHandler restControllerExceptionHandler;
  private String expectedMessage;
  private ResponseEntity<ErrorMessage> response;

  @Test
  public void handleInvalidAuthHeader_should_returnCorrectResponse() {
    InvalidAuthHeaderException e = new InvalidAuthHeaderException();
    response = restControllerExceptionHandler.handleInvalidAuthHeader(e);
    expectedMessage = InvalidAuthHeaderException.MESSAGE;

    assertEquals(expectedMessage, response.getBody().getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void handleJwtExceptions_should_returnCorrectResponse() {
    JwtException e = new JwtException("Token is expired");
    response = restControllerExceptionHandler.handleJwtExceptions(e);
    expectedMessage = e.getMessage();

    assertEquals(expectedMessage, response.getBody().getMessage());
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  public void handleMissingRequestHeader_should_returnCorrectResponse() {
    MissingRequestHeaderException e = mock(MissingRequestHeaderException.class);
    when(e.getMessage()).thenReturn("Authorization header is missing");
    response = restControllerExceptionHandler.handleMissingRequestHeaders(e);
    expectedMessage = e.getMessage();

    assertEquals(expectedMessage, response.getBody().getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

}