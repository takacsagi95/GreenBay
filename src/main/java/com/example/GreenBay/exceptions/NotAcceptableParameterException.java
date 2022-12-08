package com.example.GreenBay.exceptions;

public class NotAcceptableParameterException extends RuntimeException {
  public final String parameter;

  public NotAcceptableParameterException(String message, String parameter) {
    super(message);
    this.parameter = parameter;
  }

  public String getParameter() {
    return parameter;
  }
}
