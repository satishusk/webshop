package com.kazhukov.webshop.exceptions;

public class ImageNotFoundException extends RuntimeException{
  public ImageNotFoundException(long id) {
    super(String.format("Image with id '%s' not found", id));
  }

  public ImageNotFoundException(String message) {
    super(message);
  }
}
