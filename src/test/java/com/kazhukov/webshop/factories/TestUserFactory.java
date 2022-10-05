package com.kazhukov.webshop.factories;

import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.data.entities.User;
import com.kazhukov.webshop.services.factories.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class TestUserFactory {
  private final UserFactory userFactory;
  private final TestImageFactory testImageFactory;
  private int userGeneratedCount = 0;

  @Autowired
  public TestUserFactory(UserFactory userFactory, TestImageFactory testImageFactory) {
    this.userFactory = userFactory;
    this.testImageFactory = testImageFactory;
  }

  public User generate() {
    return generate(new HashSet<>(Set.of(new RoleDTO("ROLE_USER"))));
  }

  public User generate(Set<RoleDTO> roleDTOSet) {
    UserDTO userDTO = new UserDTO(
      "testUser" + userGeneratedCount,
      "testPassword" + userGeneratedCount,
      "testemail" + userGeneratedCount + "@email.com",
      String.valueOf(userGeneratedCount),
      testImageFactory.generate()
    );
    userGeneratedCount++;
    return userFactory.generate(userDTO, roleDTOSet);
  }
}
