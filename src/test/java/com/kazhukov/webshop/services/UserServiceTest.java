package com.kazhukov.webshop.services;

import com.kazhukov.webshop.asserts.UserAssert;
import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.entities.Image;
import com.kazhukov.webshop.entities.User;
import com.kazhukov.webshop.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
class UserServiceTest {
  private final UserService userService;
  private final UserAssert userAssert;

  @Autowired
  public UserServiceTest(UserService userService, UserAssert userAssert) {
    this.userService = userService;
    this.userAssert = userAssert;
  }

  @Test
  void createUser() {
    UserDTO testUserDTO = createTestUserDTO();
    User testUser = userService.create(testUserDTO);
    userAssert.assertEqualsUsers(testUserDTO, testUser);
  }

  @Test
  void deleteUser() {
    UserDTO testUserDTO = createTestUserDTO();
    User savedUser = userService.create(testUserDTO);

    long userId = savedUser.getId();
    long userCountBefore = userService.count();
    userService.delete(userId);
    long userCountAfter = userService.count();

    Assertions.assertEquals(userCountBefore - 1, userCountAfter);
    Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserById(userId));
  }

  @Test
  void editUser() {
    UserDTO testUserDTO1 = createTestUserDTO();
    UserDTO testUserDTO2 = createTestUserDTO();
    testUserDTO2.setUsername("test2");
    testUserDTO2.setPassword("qwerty");

    User savedUser = userService.create(testUserDTO1);
    long countBefore = userService.count();
    User editedUser = userService.edit(savedUser.getId(), testUserDTO2);
    long countAfter = userService.count();
    User presentUser = userService.getUserById(savedUser.getId());

    Assertions.assertEquals(countBefore, countAfter);
    userAssert.assertEqualsUsers(savedUser, editedUser);
    Assertions.assertEquals(editedUser, presentUser);
  }

  @Test
  void userByUsernameIsPresent() {
    UserDTO testUserDTO = createTestUserDTO();
    userService.create(testUserDTO);

    Assertions.assertTrue(userService.userByUsernameIsPresent(testUserDTO.getUsername()));
  }

  @Test
  void getUserByUsername() {
    UserDTO testUserDTO = createTestUserDTO();
    userService.create(testUserDTO);

    User userByUsername = userService.getUserByUsername(testUserDTO.getUsername());

    userAssert.assertEqualsUsers(testUserDTO, userByUsername);
  }

  @Test
  void getUserById() {
    UserDTO testUserDTO = createTestUserDTO();
    User presentUser = userService.create(testUserDTO);

    User userById = userService.getUserById(presentUser.getId());

    userAssert.assertEqualsUsers(testUserDTO, userById);
    userAssert.assertEqualsUsers(presentUser, userById);
  }

  @Test
  void getAll() {
    long usersCountBefore = userService.count();
    List<User> createdUsers = new ArrayList<>();
    UserDTO testUserDTO0 = createTestUserDTO();
    User testUser0 = userService.create(testUserDTO0);
    createdUsers.add(testUser0);

    UserDTO testUserDTO1 = createTestUserDTO();
    testUserDTO1.setUsername("test1");
    testUserDTO1.setPhoneNumber("12938123");
    testUserDTO1.setEmail("qwerty@email.com");
    User testUser1 = userService.create(testUserDTO1);
    createdUsers.add(testUser1);

    List<User> all = userService.getAll();
    Map<Long, User> userMap = all.stream().collect(Collectors.toMap(User::getId, user -> user));

    Assertions.assertEquals(2, all.size() - usersCountBefore);
    createdUsers.forEach(user -> {
      Assertions.assertTrue(userMap.containsKey(user.getId()));
      User mappedUser = userMap.get(user.getId());
      Assertions.assertEquals(user, mappedUser);
    });
  }

  @Test
  void activateUser() {
    UserDTO testUserDTO = createTestUserDTO();
    User presentUser = userService.create(testUserDTO);
    userService.activateUser(presentUser.getId());

    Assertions.assertTrue(presentUser.isActive());
  }

  @Test
  void deactivateUser() {
    UserDTO testUserDTO = createTestUserDTO();
    User presentUser = userService.create(testUserDTO);
    userService.deactivateUser(presentUser.getId());
    presentUser = userService.getUserById(presentUser.getId());

    Assertions.assertFalse(presentUser.isActive());
  }

  @Test
  void count() {
    long countBefore = userService.count();
    UserDTO testUserDTO = createTestUserDTO();
    userService.create(testUserDTO);

    long countAfter = userService.count();

    Assertions.assertEquals(countBefore + 1, countAfter);
  }

  private UserDTO createTestUserDTO() {
    String path ="D:\\webshop\\src\\test\\java\\com\\kazhukov\\webshop\\resources\\test.png";
    Image testUserAvatar = new Image(new MockMultipartFile("testName", getFileContent(path)));
    return new UserDTO("testUser", "testPassword", "testemail@email.com", "+79053247566", testUserAvatar);
  }

  private byte[] getFileContent(String path) {
    try (InputStream is = new FileInputStream(path)) {
      return is.readAllBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
