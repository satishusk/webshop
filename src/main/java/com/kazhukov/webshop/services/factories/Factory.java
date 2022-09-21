package com.kazhukov.webshop.services.factories;

public interface Factory<By, To> {
  To generate(By by);
}
