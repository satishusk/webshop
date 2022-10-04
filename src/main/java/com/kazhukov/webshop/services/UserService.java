package com.kazhukov.webshop.services;

import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.data.entities.User;

import java.util.List;

public interface UserService {
  User create(User user);
  boolean userByUsernameIsPresent(String username);
  User getUserByUsername(String username);
  User getUserById(long id);
  List<User> getAll();
  void delete(long id);
  User edit(long id, User user);
  void activateUser(long id);
  void deactivateUser(long id);
  long count();
}
