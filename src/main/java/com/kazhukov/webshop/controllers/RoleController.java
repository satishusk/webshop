package com.kazhukov.webshop.controllers;

import com.kazhukov.webshop.entities.Role;
import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import com.kazhukov.webshop.services.RoleServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {
  private final RoleServiceDefault roleServiceDefault;

  @Autowired
  public RoleController(RoleServiceDefault roleServiceDefault) {
    this.roleServiceDefault = roleServiceDefault;
  }

  @GetMapping("/roles/{name}")
  public Role findByName(@PathVariable("name") String name) {
    return roleServiceDefault.findByName(name);
  }

  @PostMapping("/roles")
  public Role create(@RequestBody RoleDTO roleDto) {
    return roleServiceDefault.create(new Role(roleDto.getName()));
  }

  @DeleteMapping("/roles/{name}")
  public void delete(@PathVariable("name") String name) {
    roleServiceDefault.delete(new Role(name));
  }
}
