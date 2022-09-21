package com.kazhukov.webshop.controllers.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public final ResponseEntity<RuntimeException> handleAllRuntimeExceptions(RuntimeException ex) {
    return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}