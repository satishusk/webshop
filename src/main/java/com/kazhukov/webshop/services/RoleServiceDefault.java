package com.kazhukov.webshop.services;

import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.data.entities.Role;
import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import com.kazhukov.webshop.data.exceptions.EntityAlreadyExistsException;
import com.kazhukov.webshop.data.exceptions.EntityNotFoundException;
import com.kazhukov.webshop.data.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RoleServiceDefault implements RoleService{
  private final RoleRepository roleRepository;

  @Autowired
  public RoleServiceDefault(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role create(Role role) {
    if (roleIsPresent(role)) {
      throw new EntityAlreadyExistsException(role);
    }
    return roleRepository.save(role);
  }

  private boolean roleIsPresent(Role role) {
    return roleRepository.findByName(role.getName()) != null;
  }

  @Override
  public void delete(long id) {
    roleRepository.deleteById(id);
  }

  @Override
  public Set<Role> findAll() {
    return new HashSet<>(roleRepository.findAll());
  }

  @Override
  public Optional<Role> findByName(String name) {
    Role role = roleRepository.findByName(name);
    if (role == null) {
      return Optional.empty();
    } else {
      return Optional.of(role);
    }
  }

  @Override
  public boolean isPresentRoleInAuthorities(Collection<? extends GrantedAuthority> authorities, Role role) {
    return authorities.stream().anyMatch(ga -> ga.getAuthority().equals(role.getName()));
  }

  @Override
  public Set<Role> persistExistsRoles(Set<Role> roles) {
    return roles.stream()
      .map(role -> findByName(role.getName()).orElse(null))
      .filter(Objects::nonNull)
      .collect(Collectors.toSet());
  }
}
