package com.kazhukov.webshop.services;

import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import com.kazhukov.webshop.entities.Role;
import com.kazhukov.webshop.exceptions.EntityAlreadyExistsException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public interface RoleService {
  Role create(Role role);
  void delete(Role role);
  Set<Role> findAll();
  Role findByName(String name);
  Set<Role> findRolesInDTO(Set<RoleDTO> estimatedRoles);
  boolean isPresentRoleInAuthorities(Collection<? extends GrantedAuthority> authorities, Role role);
}
