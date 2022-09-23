package com.kazhukov.webshop.asserts;

import com.kazhukov.webshop.entities.Role;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Component;

@Component
public class RoleAssert {

  public void assertEqualsRoles(Role expected, Role actual) {
    Assertions.assertEquals(expected.getName(), actual.getName());
  }
}
