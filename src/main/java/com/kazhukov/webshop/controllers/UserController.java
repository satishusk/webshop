package com.kazhukov.webshop.controllers;

import com.kazhukov.webshop.entities.User;
import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.exceptions.EntityAlreadyExistsException;
import com.kazhukov.webshop.services.RoleService;
import com.kazhukov.webshop.services.RoleServiceDefault;
import com.kazhukov.webshop.services.UserService;
import com.kazhukov.webshop.services.UserServiceDefault;
import com.kazhukov.webshop.services.factories.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users")
  public User registration(UserDTO userDto, Model model) {
    return userService.create(userDto);
    //"redirect:/users/login";
  }

  @GetMapping("/users")
  public List<User> findAll() {
    return userService.getAll();
  }

  @GetMapping("/users/{id}")
  public User findById(@PathVariable("id") long id) {
    return userService.getUserById(id);
  }

  @PatchMapping("/users/{id}/activate")
  public void activateUser(@PathVariable("id") long id) {
    userService.activateUser(id);
  }

  @PatchMapping("/users/{id}/deactivate")
  public void deactivateUser(@PathVariable("id") long id) {
    userService.deactivateUser(id);
  }

  @PutMapping("/users/{id}")
  public User edit(@PathVariable("id") long id, @RequestBody UserDTO userDto) {
    return userService.edit(id, userDto);
  }

  @DeleteMapping("/users/{id}")
  public void delete(@PathVariable("id") long id) {
    userService.delete(id);
  }
}
