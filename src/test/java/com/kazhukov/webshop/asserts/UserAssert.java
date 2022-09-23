package com.kazhukov.webshop.asserts;

import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.entities.Image;
import com.kazhukov.webshop.entities.User;
import com.kazhukov.webshop.services.PasswordService;
import com.kazhukov.webshop.services.RoleService;
import com.kazhukov.webshop.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAssert {
  private final RoleService roleService;
  private final ImageAssert imageAssert;
  private final PasswordService passwordService;

  @Autowired
  public UserAssert(RoleService roleService, ImageAssert imageAssert, PasswordService passwordService) {
    this.roleService = roleService;
    this.imageAssert = imageAssert;
    this.passwordService = passwordService;
  }

  public void assertEqualsUsers(UserDTO expected, User actual) {
    Assertions.assertNotNull(actual);
    Assertions.assertEquals(expected.getUsername(), actual.getUsername());
    Assertions.assertTrue(passwordService.matches(expected.getPassword(), actual.getPassword()));
    Assertions.assertEquals(expected.getEmail(), actual.getEmail());
    Assertions.assertIterableEquals(roleService.findRolesInDTOs(expected.getRoleDTOSet()), actual.getRoles());
    Assertions.assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    Assertions.assertTrue(actual.isActive());
    imageAssert.assertEqualsImages(new Image(expected.getAvatarFile()), actual.getAvatar());
    if (actual.getProducts() != null) Assertions.assertTrue(actual.getProducts().isEmpty());
  }

  public void assertEqualsUsers(User expected, User actual) {
    Assertions.assertNotNull(actual);
    Assertions.assertEquals(expected.getUsername(), actual.getUsername());
    Assertions.assertEquals(expected.getPassword(), actual.getPassword());
    Assertions.assertEquals(expected.getEmail(), actual.getEmail());
    Assertions.assertIterableEquals(expected.getRoles(), actual.getRoles());
    Assertions.assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    Assertions.assertTrue(actual.isActive());
    imageAssert.assertEqualsImages(expected.getAvatar(), actual.getAvatar());
    if (actual.getProducts() != null) Assertions.assertTrue(actual.getProducts().isEmpty());
  }
}
