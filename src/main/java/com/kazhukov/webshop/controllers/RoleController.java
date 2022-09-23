package com.kazhukov.webshop.controllers;

import com.kazhukov.webshop.entities.Role;
import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import com.kazhukov.webshop.services.RoleServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {
  private final RoleServiceDefault roleServiceDefault;

  @Autowired
  public RoleController(RoleServiceDefault roleServiceDefault) {
    this.roleServiceDefault = roleServiceDefault;
  }

  @GetMapping("/{name}")
  public Role findByName(@PathVariable("name") String name) {
    return roleServiceDefault.findByName(name);
  }

  @PostMapping("/add")
  public Role create(@RequestBody RoleDTO roleDto) {
    return roleServiceDefault.create(roleDto);
  }

  @PostMapping("/delete/{id}")
  public void delete(@PathVariable("id") long id) {
    roleServiceDefault.delete(id);
  }
}
