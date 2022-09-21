package com.kazhukov.webshop.services.factories;

import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.entities.User;
import com.kazhukov.webshop.services.PasswordService;
import com.kazhukov.webshop.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory implements Factory<UserDTO, User>{
  private final PasswordService passwordService;
  private final RoleService roleService;

  @Autowired
  public UserFactory(PasswordService passwordService, RoleService roleService) {
    this.passwordService = passwordService;
    this.roleService = roleService;
  }

  @Override
  public User generate(UserDTO userDTO) {
    User user = new User(userDTO, roleService.findRolesInDTO(userDTO.getRoleDTOSet()));
    encryptPassword(user);
    return user;
  }

  private void encryptPassword(User user) {
    user.setPassword(passwordService.encryptPassword(user.getPassword()));
  }
}
