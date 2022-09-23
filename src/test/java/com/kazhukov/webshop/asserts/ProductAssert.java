package com.kazhukov.webshop.asserts;

import com.kazhukov.webshop.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Component;

@Component
public class ProductAssert {

  public void assertEqualsProducts(Product expected, Product actual) {
    Assertions.assertEquals(expected.getTitle(), actual.getTitle());
    Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    Assertions.assertEquals(expected.getPrice(), actual.getPrice());
    Assertions.assertEquals(expected.getCity(), actual.getCity());
    Assertions.assertEquals(expected.getPreviewImageId(), actual.getPreviewImageId());
    Assertions.assertEquals(expected.getDateOfCreated(), actual.getDateOfCreated());
  }
}
