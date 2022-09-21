package com.kazhukov.webshop.services;

import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.entities.User;
import com.kazhukov.webshop.exceptions.EntityAlreadyExistsException;
import com.kazhukov.webshop.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {
  User create(UserDTO userDTO);
  boolean userByUsernameIsPresent(String username);
  User getUserByUsername(String username);
  User getUserById(long id);
  List<User> getAll();
  User delete(long id);
  User edit(long id, UserDTO userDTO);
  void activateUser(long id);
  void deactivateUser(long id);
}
