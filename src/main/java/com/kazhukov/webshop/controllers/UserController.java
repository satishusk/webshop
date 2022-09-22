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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  private final RoleService roleService;

  @Autowired
  public UserController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }

  @GetMapping("/registration")
  public String registrationPage() {
    return "registration";
  }

  @PostMapping("/registration")
  public String registration(UserDTO userDto, Model model) {
    try {
      userService.create(userDto);
      return "redirect:/users/login";
    } catch (EntityAlreadyExistsException e) {
      String errorMessage = "User with username: " + userDto.getUsername() + " already exists!";
      model.addAttribute("errorMessage", errorMessage);
      return "registration";
    }
  }

  @GetMapping("/edit")
  public String editCurrentPage(Model model, Principal principal) {
    User userByUsername = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", userByUsername);
    model.addAttribute("roles", roleService.findAll());
    return "edit-current";
  }

  @PostMapping("/edit")
  public User editCurrent(UserDTO userDto, Principal principal) {
    User userByUsername = userService.getUserByUsername(principal.getName());
    return userService.edit(userByUsername.getId(), userDto);
  }

  @GetMapping("/admin")
  public String findAll(Model model) {
    model.addAttribute("users", userService.getAll());
    return "users";
  }

  @GetMapping("/admin/{id}")
  public String findById(@PathVariable("id") long id, Model model) {
    model.addAttribute("user", userService.getUserById(id));
    return "user-info";
  }

  @PostMapping("/admin/activate/{id}")
  public String activateUser(@PathVariable("id") long id) {
    userService.activateUser(id);
    return "redirect:/users/admin";
  }

  @PostMapping("/admin/deactivate/{id}")
  public String deactivateUser(@PathVariable("id") long id) {
    userService.deactivateUser(id);
    return "redirect:/users/admin";
  }

  @GetMapping("/admin/edit/{id}")
  public String editPage(@PathVariable("id") long id, Model model) {
    User userById = userService.getUserById(id);
    model.addAttribute("user", userById);
    model.addAttribute("roles", roleService.findAll());
    return "edit";
  }

  @PostMapping("/admin/edit/{id}")
  public String edit(@PathVariable("id") long id, @RequestBody UserDTO userDto) {
    userService.edit(id, userDto);
    return "redirect:/users/admin";
  }

  @PostMapping("/admin/delete/{id}")
  public String delete(@PathVariable("id") long id) {
    userService.delete(id);
    return "redirect:/users/admin";
  }
}
