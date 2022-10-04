package com.kazhukov.webshop.asserts;

import com.kazhukov.webshop.data.entities.Product;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Component;

@Component
public class ProductAssert {

  public void assertEqualsProducts(Product expected, Product actual) {
    Assertions.assertEquals(expected.getTitle(), actual.getTitle());
    Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    Assertions.assertEquals(expected.getPrice(), actual.getPrice());
    Assertions.assertEquals(expected.getCity(), actual.getCity());
  }

  public void assertNotEqualsProducts(Product expected, Product actual) {
    Assertions.assertNotEquals(expected.getTitle(), actual.getTitle());
    Assertions.assertNotEquals(expected.getDescription(), actual.getDescription());
    Assertions.assertNotEquals(expected.getPrice(), actual.getPrice());
    Assertions.assertNotEquals(expected.getCity(), actual.getCity());
  }
}
