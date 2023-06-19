package com.syscode.addressserviceapplication.errorhandling.exceptions;

public class InvalidUuidFormatException extends RuntimeException {

  public static final String MESSAGE = "UUID is invalid.";

  public InvalidUuidFormatException() {
    super(MESSAGE);
  }

}
