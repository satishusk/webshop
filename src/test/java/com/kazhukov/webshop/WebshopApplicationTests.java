package com.kazhukov.webshop;

import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.entities.Image;
import com.kazhukov.webshop.entities.User;
import com.kazhukov.webshop.services.PasswordService;
import com.kazhukov.webshop.services.RoleService;
import com.kazhukov.webshop.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;

@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceTests {
  private final UserService userService;
  private final PasswordService passwordService;
  private final RoleService roleService;

  @Autowired
  public UserServiceTests(UserService userService, PasswordService passwordService, RoleService roleService) {
    this.userService = userService;
    this.passwordService = passwordService;
    this.roleService = roleService;
  }

  @Test
  void createUserTest() {
    String path ="D:\\webshop\\src\\test\\java\\com\\kazhukov\\webshop\\resources\\0_AqFiJp2_TrUgDtKN.png";
    Image testUserAvatar = new Image(new MockMultipartFile("testName", getFileContent(path)));
    UserDTO testUserDTO = new UserDTO("testUser", "testPassword",
      "testemail@email.com", "+79053247566", testUserAvatar);
    User testUser = userService.create(testUserDTO);

    assertEqualsUsers(testUserDTO, testUser);
  }

  private byte[] getFileContent(String path) {
    try (InputStream is = new FileInputStream(path)) {
      return is.readAllBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void assertEqualsUsers(UserDTO from, User actual) {
    Assertions.assertNotNull(actual);
    Assertions.assertEquals(from.getUsername(), actual.getUsername());
    Assertions.assertEquals(from.getEmail(), actual.getEmail());
    Assertions.assertEquals(passwordService.encryptPassword(from.getPassword()), actual.getPassword());
    Assertions.assertEquals(new Image(from.getAvatarFile()), actual.getAvatar());
    Assertions.assertEquals(from.getPhoneNumber(), actual.getPhoneNumber());
    Assertions.assertEquals(roleService.findRolesInDTO(from.getRoleDTOSet()), actual.getRoles());
  }
}
