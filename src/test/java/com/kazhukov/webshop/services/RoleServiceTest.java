package com.kazhukov.webshop.services;

import com.kazhukov.webshop.asserts.RoleAssert;
import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import com.kazhukov.webshop.data.entities.Role;
import com.kazhukov.webshop.data.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
class RoleServiceTest {
  private final RoleService roleService;
  private final RoleAssert roleAssert;

  @Autowired
  public RoleServiceTest(RoleService roleService, RoleAssert roleAssert) {
    this.roleService = roleService;
    this.roleAssert = roleAssert;
  }

  @Test
  void create() {
    RoleDTO roleDTO = generateRoleDTO();
    Role role = roleService.create(new Role(roleDTO.getName()));
    roleAssert.assertEqualsRoles(new Role(roleDTO.getName()), role);
  }

  @Test
  void delete() {
    RoleDTO roleDTO = generateRoleDTO();
    Role role = roleService.create(new Role(roleDTO.getName()));

    long countBefore = roleService.findAll().size();
    roleService.delete(role.getId());
    long countAfter = roleService.findAll().size();

    Assertions.assertEquals(countBefore - 1, countAfter);
    String roleName = roleDTO.getName();
      Optional<Role> optionalRole = roleService.findByName(roleName);
    Assertions.assertFalse(optionalRole.isPresent());
  }

  @Test
  void findByName() {
    RoleDTO roleDTO = generateRoleDTO();
    Role role = roleService.create(new Role(roleDTO.getName()));
    Role roleByName = roleService.findByName(role.getName()).orElseThrow(() -> new EntityNotFoundException(role.getName()));

    roleAssert.assertEqualsRoles(role, roleByName);
  }

  @Test
  void filterExistsRoles() {
    Set<RoleDTO> roleDTOs = Set.of(
      new RoleDTO("ROLE_TEST_1"),
      new RoleDTO("ROLE_TEST_2"),
      new RoleDTO("ROLE_TEST_3")
    );
    Set<Role> roles = roleDTOs.stream().map(roleDTO -> roleService.create(new Role(roleDTO.getName()))).collect(Collectors.toSet());

    Set<RoleDTO> wrongRoleDTOs = Set.of(
      new RoleDTO("ROLE_TEST_0"),
      new RoleDTO("ROLE_TEST"),
      new RoleDTO("ROLE_TEST_3")
    );

    Assertions.assertNotEquals(
      roles,
      roleService.persistExistsRoles(wrongRoleDTOs.stream()
        .map(roleDTO -> new Role(roleDTO.getName()))
        .collect(Collectors.toSet()))
    );
  }

  @Test
  void isPresentRoleInAuthorities() {
    var roleDTOSet = Set.of(
      new RoleDTO("ROLE_TEST_1"),
      new RoleDTO("ROLE_TEST_2"),
      new RoleDTO("ROLE_TEST_3")
    );
    roleDTOSet.forEach(roleDTO -> roleService.create(new Role(roleDTO.getName())));

    var grantedAuthorities = roleDTOSet.stream()
      .map(roleDTO -> new SimpleGrantedAuthority(roleDTO.getName()))
      .collect(Collectors.toCollection(ArrayList::new));
    var grantedAuthoritiesFirstRole = roleDTOSet.stream().map(roleDTO -> new Role(roleDTO.getName())).findFirst().get();
    var roleIsPresentInAuthorities = roleService.isPresentRoleInAuthorities(grantedAuthorities, grantedAuthoritiesFirstRole);

    Assertions.assertTrue(roleIsPresentInAuthorities);
  }

  private RoleDTO generateRoleDTO() {
    return new RoleDTO("ROLE_TEST");
  }
}