package com.kazhukov.webshop.asserts;

import com.kazhukov.webshop.entities.Image;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageAssert {
  private final ProductAssert productAssert;

  @Autowired
  public ImageAssert(ProductAssert productAssert) {
    this.productAssert = productAssert;
  }

  public void assertEqualsImages(Image expected, Image actual) {
    Assertions.assertEquals(expected.getName(), actual.getName());
    Assertions.assertEquals(expected.getOriginalFilename(), actual.getOriginalFilename());
    Assertions.assertEquals(expected.getSize(), actual.getSize());
    Assertions.assertEquals(expected.getContentType(), actual.getContentType());
    Assertions.assertArrayEquals(expected.getContent(), actual.getContent());
    if (expected.getProduct() != null) productAssert.assertEqualsProducts(expected.getProduct(), actual.getProduct());
    Assertions.assertEquals(expected.isBeenTransfer(), actual.isBeenTransfer());
  }
}
