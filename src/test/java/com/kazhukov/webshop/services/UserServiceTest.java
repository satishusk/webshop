package com.kazhukov.webshop.services;

import com.kazhukov.webshop.factories.TestUserFactory;
import com.kazhukov.webshop.asserts.UserAssert;
import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import com.kazhukov.webshop.data.entities.User;
import com.kazhukov.webshop.data.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
class UserServiceTest {
  private final UserService userService;
  private final TestUserFactory testUserFactory;
  private final UserAssert userAssert;

  @Autowired
  public UserServiceTest(UserService userService, TestUserFactory testUserFactory, UserAssert userAssert) {
    this.userService = userService;
    this.testUserFactory = testUserFactory;
    this.userAssert = userAssert;
  }

  @Test
  void createUser() {
    User expectedUser = testUserFactory.generate();
    User actualUser = userService.create(expectedUser);
    userAssert.assertEqualsUsers(expectedUser, actualUser);
  }

  @Test
  void deleteUser() {
    User user = userService.create(testUserFactory.generate());

    long userId = user.getId();
    long userCountBefore = userService.count();
    userService.delete(userId);
    long userCountAfter = userService.count();

    Assertions.assertEquals(userCountBefore - 1, userCountAfter);
    Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserById(userId));
  }

  @Test
  void editUser() {
    Set<RoleDTO> roleDTOSet = Set.of(
      new RoleDTO("ROLE_USER"),
      new RoleDTO("ROLE_ADMIN")
    );

    User savedUser = userService.create(testUserFactory.generate());
    long countBefore = userService.count();
    User editedUser = userService.edit(savedUser.getId(), testUserFactory.generate(roleDTOSet));
    long countAfter = userService.count();
    User presentUser = userService.getUserById(savedUser.getId());

    Assertions.assertEquals(countBefore, countAfter);
    userAssert.assertEqualsUsers(savedUser, editedUser);
    userAssert.assertEqualsUsers(editedUser, presentUser);
  }

  @Test
  void userByUsernameIsPresent() {
    User user = userService.create(testUserFactory.generate());
    Assertions.assertTrue(userService.userByUsernameIsPresent(user.getUsername()));
  }

  @Test
  void getUserByUsername() {
    User user = userService.create(testUserFactory.generate());
    User userByUsername = userService.getUserByUsername(user.getUsername());
    userAssert.assertEqualsUsers(user, userByUsername);
  }

  @Test
  void getUserById() {
    User presentUser = userService.create(testUserFactory.generate());
    User userById = userService.getUserById(presentUser.getId());
    userAssert.assertEqualsUsers(presentUser, userById);
  }

  @Test
  void getAll() {
    long usersCountBefore = userService.count();
    List<User> createdUsers = new ArrayList<>();
    createdUsers.add(userService.create(testUserFactory.generate()));
    createdUsers.add(userService.create(testUserFactory.generate()));

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
    User presentUser = userService.create(testUserFactory.generate());
    userService.activateUser(presentUser.getId());
    Assertions.assertTrue(presentUser.isActive());
  }

  @Test
  void deactivateUser() {
    User presentUser = userService.create(testUserFactory.generate());
    userService.deactivateUser(presentUser.getId());
    presentUser = userService.getUserById(presentUser.getId());

    Assertions.assertFalse(presentUser.isActive());
  }

  @Test
  void count() {
    long countBefore = userService.count();
    userService.create(testUserFactory.generate());
    long countAfter = userService.count();

    Assertions.assertEquals(countBefore + 1, countAfter);
  }
}
