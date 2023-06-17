package com.syscode.profileserviceapplication.errorhandling;

import lombok.Data;

@Data
public class ErrorMessage {

  private String status;
  private String message;

  public ErrorMessage() {
    this.status = "error";
  }

  public ErrorMessage(String message) {
    this();
    this.message = message;
  }

}
