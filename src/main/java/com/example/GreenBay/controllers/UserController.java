package com.example.GreenBay.controllers;

import com.example.GreenBay.exceptions.InvalidPasswordException;
import com.example.GreenBay.exceptions.MissingParameterException;
import com.example.GreenBay.exceptions.NotAcceptableParameterException;
import com.example.GreenBay.exceptions.UserExistsException;
import com.example.GreenBay.exceptions.UserNotExistsException;
import com.example.GreenBay.models.dtos.ErrorDto;
import com.example.GreenBay.models.dtos.LoginMessageOK;
import com.example.GreenBay.models.dtos.ResponseDto;
import com.example.GreenBay.models.dtos.UserLoginDto;
import com.example.GreenBay.models.entities.User;
import com.example.GreenBay.services.TokenService;
import com.example.GreenBay.services.UserService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;
  private final TokenService tokenService;

  public UserController(UserService userService, TokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @PostMapping("/signup")
  public ResponseEntity<ResponseDto> registerNewUser(@RequestBody User user) {
    if (user.getUsername() == null) {
      throw new MissingParameterException("/signup", "Username is required");
    }
    if (user.getPassword() == null) {
      throw new MissingParameterException("/signup", "Password is required");
    }
    if (user.getPassword().length() < 6) {
      throw new NotAcceptableParameterException("/signup",
          "Password length should be longer than 6 characters");
    }
    if (userService.userExists(user.getUsername())) {
      return ResponseEntity.status(200).body(new ErrorDto("This username is already under usage"));
    }
    // UriComponentsBuilder with additional static factory methods to create links based on the
    // current HttpServletRequest.
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/user/signup")
        .toUriString());
    if (userService.userExists(user.getUsername())) {
      throw new UserExistsException("/signup");
    }
    if (userService.validUser(user)) {
      return ResponseEntity.created(uri)
          .body(userService.convertUserToUserRegistrationDto(userService.saveReturnUser(user)));
    }
    throw new MissingParameterException("/signup", "Bad request");
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
    if (userLoginDto == null || userLoginDto.getUsername().equals("") && userLoginDto.getPassword()
        .equals("")) {
      throw new MissingParameterException("/login", "username, password");
    }
    if (userLoginDto.getUsername().equals("") || userLoginDto.getUsername().isEmpty()) {
      throw new MissingParameterException("/login", "username");
    }
    if (userLoginDto.getPassword().equals("") || userLoginDto.getPassword().isEmpty()) {
      throw new MissingParameterException("/login", "password");
    }
    if (userService.findUserByUsername(userLoginDto.getUsername()) == null) {
      throw new UserNotExistsException("/login");
    }
    if (!userService.passwordIsCorrect(
        userLoginDto.getPassword(),
        userService.findUserByUsername(userLoginDto.getUsername()).getPassword())) {
      throw new InvalidPasswordException("/login");
    }
    org.springframework.security.core.userdetails.User loggingUser =
        (org.springframework.security.core.userdetails.User) tokenService.loadUserByUsername(
            userLoginDto.getUsername());
    return ResponseEntity.status(200)
        .body(new LoginMessageOK("OK", tokenService.getToken(loggingUser)));
  }
}
