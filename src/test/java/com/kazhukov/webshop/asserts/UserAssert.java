package com.kazhukov.webshop.asserts;

import com.kazhukov.webshop.data.entities.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAssert {
  private final ImageAssert imageAssert;

  public void assertEqualsUsers(User expected, User actual) {
    Assertions.assertNotNull(actual);
    Assertions.assertEquals(expected.getUsername(), actual.getUsername());
    Assertions.assertEquals(expected.getPasswordHash(), actual.getPasswordHash());
    Assertions.assertEquals(expected.getEmail(), actual.getEmail());
    Assertions.assertIterableEquals(expected.getRoles(), actual.getRoles());
    Assertions.assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    Assertions.assertEquals(expected.isActive(), actual.isActive());
    imageAssert.assertEqualsImages(expected.getAvatar(), actual.getAvatar());
    if (actual.getProducts() != null) Assertions.assertTrue(actual.getProducts().isEmpty());
  }
}
