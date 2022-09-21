package com.kazhukov.webshop.exceptions;

public class ProductNotFoundException extends RuntimeException{

  public ProductNotFoundException(Long id) {
    this(String.format("Product with id: '%s' not found", id));
  }
  public ProductNotFoundException(String message) {
    super(message);
  }
}
