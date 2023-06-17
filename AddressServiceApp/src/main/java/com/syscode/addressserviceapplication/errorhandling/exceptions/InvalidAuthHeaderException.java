package com.syscode.addressserviceapplication.errorhandling.exceptions;

public class InvalidAuthHeaderException extends RuntimeException {

  public static final String MESSAGE = "The provided 'Authorization' header is invalid.";

  public InvalidAuthHeaderException() {
    super(MESSAGE);
  }

}
