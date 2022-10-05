package com.kazhukov.webshop.data.exceptions;

public class EntityNotFoundException extends RuntimeException{
  public EntityNotFoundException(String username) {
    super(String.format("Entity with name: '%s' not found", username));
  }

  public EntityNotFoundException(long id) {
    super(String.format("Entity with id: '%s' not found", id));
  }
}
