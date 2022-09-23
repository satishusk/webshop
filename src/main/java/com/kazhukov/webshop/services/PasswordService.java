package com.kazhukov.webshop.services;

public interface PasswordService {
  String encryptPassword(String password);
  boolean matches(CharSequence rawPassword, String encodedPassword);
}
