package com.example.GreenBay.exceptions;

public class UserNotExistsException extends RuntimeException {
  public UserNotExistsException(String message) {
    super(message);
  }
}
