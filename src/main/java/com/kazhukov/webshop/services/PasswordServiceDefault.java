package com.kazhukov.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceDefault implements PasswordService{
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public PasswordServiceDefault(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public String encryptPassword(String password) {
    return passwordEncoder.encode(password);
  }

  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }
}
