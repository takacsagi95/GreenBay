package com.example.GreenBay.exceptions;

public class MissingParameterException extends RuntimeException {
  public final String parameter;

  public MissingParameterException(String message, String parameter) {
    super(message);
    this.parameter = parameter;
  }

  public String getParameter() {
    return parameter;
  }
}
