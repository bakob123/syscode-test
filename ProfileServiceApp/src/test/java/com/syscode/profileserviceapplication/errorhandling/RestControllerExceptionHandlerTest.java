package com.syscode.profileserviceapplication.errorhandling;

import com.syscode.profileserviceapplication.errorhandling.exceptions.AlreadyTakenException;
import com.syscode.profileserviceapplication.errorhandling.exceptions.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestControllerExceptionHandlerTest {

  @Spy
  private RestControllerExceptionHandler restControllerExceptionHandler;
  private List<FieldError> fieldErrors;
  private String expectedMessage;
  private String receivedMessage;
  private HttpStatus expectedStatus;
  private HttpStatus receivedStatus;
  private ResponseEntity<ErrorMessage> response;
  @Mock
  private FieldError fieldError;
  @Mock
  private FieldError fieldErrorTwo;
  @Mock
  private FieldError fieldErrorThree;
  @Mock
  private MethodArgumentNotValidException methodArgumentNotValidException;

  @BeforeEach
  public void setup() {
    fieldErrors = new ArrayList<>();
    fieldErrors.add(fieldError);
  }

  @Test
  public void handleMissingFields_should_returnCorrectMessage_when_singleFieldIsMissing() {
    when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("NotBlank");
    doReturn("Username is required.")
        .when(restControllerExceptionHandler).getMissingFieldsMessage(fieldErrors);

    response = restControllerExceptionHandler.handleMissingFields(methodArgumentNotValidException);
    expectedMessage = "Username is required.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingFields_should_returnCorrectMessage_when_twoFieldsAreMissing() {
    fieldErrors.add(fieldErrorTwo);
    when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("NotBlank");
    when(fieldErrorTwo.getCode()).thenReturn("NotBlank");
    doReturn("Username and password are required.")
        .when(restControllerExceptionHandler).getMissingFieldsMessage(fieldErrors);

    response = restControllerExceptionHandler.handleMissingFields(methodArgumentNotValidException);
    expectedMessage = "Username and password are required.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingFields_should_returnCorrectMessage_when_multipleFieldsAreMissing() {
    fieldErrors.add(fieldErrorTwo);
    fieldErrors.add(fieldErrorThree);
    when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("NotBlank");
    when(fieldErrorTwo.getCode()).thenReturn("NotBlank");
    when(fieldErrorThree.getCode()).thenReturn("NotBlank");
    doReturn("Username, password and email are required.")
        .when(restControllerExceptionHandler).getMissingFieldsMessage(fieldErrors);

    response = restControllerExceptionHandler.handleMissingFields(methodArgumentNotValidException);
    expectedMessage = "Username, password and email are required.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingFields_should_returnCorrectMessage_when_lengthConstraintIsViolated() {
    when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("Length");
    when(fieldError.getDefaultMessage()).thenReturn("Name must be shorter than 50 characters.");

    response = restControllerExceptionHandler.handleMissingFields(methodArgumentNotValidException);
    expectedMessage = "Name must be shorter than 50 characters.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingRequestBody_should_returnCorrectMessage() {
    response = restControllerExceptionHandler.handleHttpMessageNotReadable();
    expectedMessage = "An error occurred while processing the request body.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleAlreadyTaken_should_returnCorrectMessage() {
    AlreadyTakenException e = new AlreadyTakenException("Email is already in use.");
    response = restControllerExceptionHandler.handleAlreadyTakenException(e);
    expectedMessage = "Email is already in use.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.CONFLICT;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleStudentNotFound_should_returnCorrectMessage() {
    response = restControllerExceptionHandler.handleStudentNotFound();
    expectedMessage = StudentNotFoundException.MESSAGE;
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.NOT_FOUND;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleAlreadyResourceAccessException_should_returnCorrectMessage() {
    ResourceAccessException e = new ResourceAccessException("Could not connect");
    response = restControllerExceptionHandler.handleResourceAccess(e);
    expectedMessage = "Could not connect";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.GATEWAY_TIMEOUT;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

}