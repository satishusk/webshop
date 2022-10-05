package com.kazhukov.webshop.services;

import com.kazhukov.webshop.data.entities.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
  Role create(Role role);
  void delete(long id);
  Set<Role> findAll();
  Optional<Role> findByName(String name);
  boolean isPresentRoleInAuthorities(Collection<? extends GrantedAuthority> authorities, Role role);
  Set<Role> persistExistsRoles(Set<Role> roles);
}

