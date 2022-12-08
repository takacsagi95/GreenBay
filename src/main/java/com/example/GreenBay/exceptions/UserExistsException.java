package com.example.GreenBay.exceptions;

public class UserExistsException extends RuntimeException {
  public UserExistsException(String message) {
    super(message);
  }
}
