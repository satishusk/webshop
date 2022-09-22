package com.kazhukov.webshop.controllers;

import com.kazhukov.webshop.entities.Product;
import com.kazhukov.webshop.entities.Role;
import com.kazhukov.webshop.entities.User;
import com.kazhukov.webshop.controllers.dtos.ProductDTO;
import com.kazhukov.webshop.security.authentication.GrantedAuthentication;
import com.kazhukov.webshop.services.ProductServiceDefault;
import com.kazhukov.webshop.services.RoleServiceDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
  private final ProductServiceDefault productServiceDefault;
  private final RoleServiceDefault roleServiceDefault;

  @GetMapping("/products")
  public List<Product> findAll(GrantedAuthentication authentication) {
    return productServiceDefault.findAll();
  }

  private boolean isAdminAuthenticate(GrantedAuthentication authentication) {
    Role adminRole = roleServiceDefault.findByName("ROLE_ADMIN");
    return roleServiceDefault.isPresentRoleInAuthorities(authentication.getAuthorities(), adminRole);
  }

  @GetMapping("/products/{id}")
  public Product findById(@PathVariable("id") long id, Model model) {
    return productServiceDefault.findById(id);
  }

  @PostMapping("/products")
  public Product create(@ModelAttribute ProductDTO productDto, Principal principal) {
    return productServiceDefault.create(productServiceDefault.generateProduct(productDto, principal));
//    return "redirect:/products";
  }

  @DeleteMapping("/products/{id}")
  public void delete(@PathVariable("id") long id, Principal principal) {
    Product productById = productServiceDefault.findById(id);
    User owner = productById.getUser();
    if (!owner.getUsername().equals(principal.getName())) {
      throw new IllegalArgumentException("Attempt to remove a product by a user who does not own it ");
    }
    productServiceDefault.deleteById(id);
//    return "redirect:/products";
  }
}
