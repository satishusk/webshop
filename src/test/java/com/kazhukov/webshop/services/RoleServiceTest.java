package com.kazhukov.webshop.services;

import com.kazhukov.webshop.asserts.RoleAssert;
import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import com.kazhukov.webshop.entities.Role;
import com.kazhukov.webshop.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
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
    Role role = roleService.create(roleDTO);
    roleAssert.assertEqualsRoles(new Role(roleDTO), role);
  }

  @Test
  void delete() {
    RoleDTO roleDTO = generateRoleDTO();
    Role role = roleService.create(roleDTO);

    long countBefore = roleService.findAll().size();
    roleService.delete(role.getId());
    long countAfter = roleService.findAll().size();

    Assertions.assertEquals(countBefore - 1, countAfter);
    Assertions.assertThrows(EntityNotFoundException.class, () -> roleService.findByName(roleDTO.getName()));
  }

  @Test
  void findByName() {
    RoleDTO roleDTO = generateRoleDTO();
    Role role = roleService.create(roleDTO);
    Role roleByName = roleService.findByName(role.getName());

    roleAssert.assertEqualsRoles(role, roleByName);
  }

  @Test
  void findRolesInDTO() {
    Set<RoleDTO> roleDTOs = Set.of(
      new RoleDTO("ROLE_TEST_1"),
      new RoleDTO("ROLE_TEST_2"),
      new RoleDTO("ROLE_TEST_3")
    );
    roleDTOs.forEach(roleService::create);

    Set<RoleDTO> wrongRoleDTOs = Set.of(
      new RoleDTO("ROLE_TEST_0"),
      new RoleDTO("ROLE_TEST"),
      new RoleDTO("ROLE_TEST_3")
    );

    Assertions.assertThrows(EntityNotFoundException.class, () -> roleService.findRolesInDTOs(wrongRoleDTOs));
  }

  @Test
  void isPresentRoleInAuthorities() {
    var roleDTOSet = Set.of(
      new RoleDTO("ROLE_TEST_1"),
      new RoleDTO("ROLE_TEST_2"),
      new RoleDTO("ROLE_TEST_3")
    );
    roleDTOSet.forEach(roleService::create);

    var grantedAuthorities = roleDTOSet.stream()
      .map(roleDTO -> new SimpleGrantedAuthority(roleDTO.getName()))
      .collect(Collectors.toCollection(ArrayList::new));
    var grantedAuthoritiesFirstRole = roleDTOSet.stream().map(Role::new).findFirst().get();
    var roleIsPresentInAuthorities = roleService.isPresentRoleInAuthorities(grantedAuthorities, grantedAuthoritiesFirstRole);

    Assertions.assertTrue(roleIsPresentInAuthorities);
  }

  private RoleDTO generateRoleDTO() {
    return new RoleDTO("ROLE_TEST");
  }
}