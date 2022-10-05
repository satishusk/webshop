package com.kazhukov.webshop.services.factories;

import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.data.entities.Role;
import com.kazhukov.webshop.data.entities.User;
import com.kazhukov.webshop.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserFactory {
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserFactory(RoleService roleService, PasswordEncoder passwordEncoder) {
    this.roleService = roleService;
    this.passwordEncoder = passwordEncoder;
  }

  public User generate(UserDTO userDTO) {
    Set<RoleDTO> roleDTOSet = new HashSet<>(Set.of(new RoleDTO("ROLE_USER")));
    return generate(userDTO, roleDTOSet);
  }

  public User generate(UserDTO userDTO, Set<RoleDTO> roleDTOSet) {
    String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
    userDTO.setPassword(encodedPassword);
    Set<Role> defaultRoles = getPresentRoles(roleDTOSet);
    return new User(userDTO, defaultRoles);
  }

  private Set<Role> getPresentRoles(Set<RoleDTO> actualRoleDTOSet) {
    return roleService.persistExistsRoles(actualRoleDTOSet.stream()
      .map(roleDTO -> new Role(roleDTO.getName()))
      .collect(Collectors.toSet())
    );
  }
}
