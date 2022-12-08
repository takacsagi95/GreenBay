package com.example.GreenBay.models.dtos;

public class LoginMessageOK implements ResponseDto {
  private String status;
  private String accessToken;

  public LoginMessageOK(String status, String accessToken) {
    this.status = status;
    this.accessToken = accessToken;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
