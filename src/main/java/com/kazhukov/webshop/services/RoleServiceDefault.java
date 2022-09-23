package com.kazhukov.webshop.services;

import com.kazhukov.webshop.entities.Role;
import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import com.kazhukov.webshop.exceptions.EntityAlreadyExistsException;
import com.kazhukov.webshop.exceptions.EntityNotFoundException;
import com.kazhukov.webshop.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceDefault implements RoleService{
  private final RoleRepository roleRepository;

  @Autowired
  public RoleServiceDefault(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role create(RoleDTO roleDTO) {
    Role role = new Role(roleDTO);
    if (roleIsPresent(role)) throw new EntityAlreadyExistsException(role);
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
  public Role findByName(String name) {
    Role role = roleRepository.findByName(name);
    if (role == null) throw new EntityNotFoundException(name);
    return role;
  }

  @Override
  public Set<Role> findRolesInDTOs(Set<RoleDTO> estimatedRoles) {
    return estimatedRoles.stream()
        .map(roleDTO -> findByName(roleDTO.getName()))
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isPresentRoleInAuthorities(Collection<? extends GrantedAuthority> authorities, Role role) {
    return authorities.stream().anyMatch(ga -> ga.getAuthority().equals(role.getName()));
  }
}
