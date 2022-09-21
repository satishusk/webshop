package com.kazhukov.webshop.exceptions;

public class UserNotFoundException extends RuntimeException{
  public UserNotFoundException(String username) {
    super(String.format("User with name: '%s' not found", username));
  }

  public UserNotFoundException(long id) {
    super(String.format("User with id: '%s' not found", id));
  }
}
