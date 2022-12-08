package com.example.GreenBay.models.dtos;

public class ErrorDto implements ResponseDto {
  private String message;

  public ErrorDto() {
  }

  public ErrorDto(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
