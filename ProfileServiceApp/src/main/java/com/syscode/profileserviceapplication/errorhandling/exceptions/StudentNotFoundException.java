package com.syscode.profileserviceapplication.errorhandling.exceptions;

public class StudentNotFoundException extends RuntimeException {

  public static final String MESSAGE = "Student not found.";

  public StudentNotFoundException() {
    super(MESSAGE);
  }

}
