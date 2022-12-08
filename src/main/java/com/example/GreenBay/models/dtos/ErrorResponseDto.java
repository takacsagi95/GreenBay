package com.example.GreenBay.models.dtos;

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;

public class ErrorResponseDto {
  private final String message;
  private final HttpStatus httpStatus;
  private final ZonedDateTime timestamp;
  private final String path;

  public ErrorResponseDto(String message, HttpStatus httpStatus, ZonedDateTime timestamp,
      String path) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;
    this.path = path;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  public String getPath() {
    return path;
  }
}
