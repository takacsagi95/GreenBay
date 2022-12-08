package com.example.GreenBay.models.dtos;

public class UserRegistrationDto implements ResponseDto {
  private Long id;
  private String username;

  public UserRegistrationDto() {
  }

  public UserRegistrationDto(Long id, String userName, String password) {
    this.id = id;
    this.username = userName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
