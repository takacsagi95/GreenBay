package com.example.GreenBay.exceptions;

import static com.example.GreenBay.exceptions.ExceptionMessageConfiguration.BODY_MISSING_ERROR;
import static com.example.GreenBay.exceptions.ExceptionMessageConfiguration.INVALID_PASSWORD_ERROR;
import static com.example.GreenBay.exceptions.ExceptionMessageConfiguration.MISSING_PARAMETER_EXCEPTION;
import static com.example.GreenBay.exceptions.ExceptionMessageConfiguration.NOT_ACCEPTABLE_PARAMETER;
import static com.example.GreenBay.exceptions.ExceptionMessageConfiguration.USER_EXISTS_ERROR;
import static com.example.GreenBay.exceptions.ExceptionMessageConfiguration.USER_NOT_EXISTS_ERROR;
import static org.springframework.http.HttpStatus.CONFLICT;

import com.example.GreenBay.models.dtos.ErrorResponseDto;
import java.time.ZonedDateTime;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
  @ExceptionHandler(BodyMissingException.class)
  @ResponseStatus(value = CONFLICT)
  public ErrorResponseDto handleBodyMissingException(BodyMissingException e) {
    return new ErrorResponseDto(
        BODY_MISSING_ERROR,
        CONFLICT,
        ZonedDateTime.now(),
        e.getMessage()
    );
  }

  @ExceptionHandler(MissingParameterException.class)
  @ResponseStatus(value = CONFLICT)
  public ErrorResponseDto handleMissingParameterException(MissingParameterException e) {
    return new ErrorResponseDto(
        MISSING_PARAMETER_EXCEPTION,
        CONFLICT,
        ZonedDateTime.now(),
        e.getMessage()
    );
  }

  @ExceptionHandler(NotAcceptableParameterException.class)
  @ResponseStatus(value = CONFLICT)
  public ErrorResponseDto handleNotAcceptableParameterException(NotAcceptableParameterException e) {
    return new ErrorResponseDto(
        NOT_ACCEPTABLE_PARAMETER,
        CONFLICT,
        ZonedDateTime.now(),
        e.getMessage()
    );
  }

  @ExceptionHandler(UserExistsException.class)
  @ResponseStatus(value = CONFLICT)
  public ErrorResponseDto handleUserExistException(UserExistsException e) {
    return new ErrorResponseDto(
        USER_EXISTS_ERROR,
        CONFLICT,
        ZonedDateTime.now(),
        e.getMessage()
    );
  }

  @ExceptionHandler(UserNotExistsException.class)
  @ResponseStatus(value = CONFLICT)
  public ErrorResponseDto handleUserNotExistsException(UserNotExistsException e) {
    return new ErrorResponseDto(
        USER_NOT_EXISTS_ERROR,
        CONFLICT,
        ZonedDateTime.now(),
        e.getMessage()
    );
  }

  @ExceptionHandler(InvalidPasswordException.class)
  @ResponseStatus(value = CONFLICT)
  public ErrorResponseDto handleInvalidPasswordException(InvalidPasswordException e) {
    return new ErrorResponseDto(
        INVALID_PASSWORD_ERROR,
        CONFLICT,
        ZonedDateTime.now(),
        e.getMessage()
    );
  }
}
