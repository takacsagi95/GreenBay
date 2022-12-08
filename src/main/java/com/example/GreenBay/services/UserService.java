package com.example.GreenBay.services;

import com.example.GreenBay.models.dtos.UserRegistrationDto;
import com.example.GreenBay.models.entities.User;

public interface UserService {
  void saveUser(User user);

  User saveReturnUser(User user);

  User findUserByUsername(String username);

  boolean userExists(String username);

  boolean validUser(User user);

  boolean passwordIsCorrect(String rawPAssword, String encodedPassword);

  UserRegistrationDto convertUserToUserRegistrationDto(User user);
}
