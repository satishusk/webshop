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

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
  private final ProductServiceDefault productServiceDefault;
  private final RoleServiceDefault roleServiceDefault;

  @GetMapping
  public String findAll(Model model, GrantedAuthentication authentication) {
    model.addAttribute("products", productServiceDefault.findAll());
    model.addAttribute("authentication", authentication);
    model.addAttribute("isAdmin", isAdminAuthenticate(authentication));
    return "products";
  }

  private boolean isAdminAuthenticate(GrantedAuthentication authentication) {
    Role adminRole = roleServiceDefault.findByName("ROLE_ADMIN");
    return roleServiceDefault.isPresentRoleInAuthorities(authentication.getAuthorities(), adminRole);
  }

  @GetMapping("/{id}")
  public String findById(@PathVariable("id") long id, Model model) {
    Product product = productServiceDefault.findById(id);
    model.addAttribute("product", product);
    model.addAttribute("images", product.getImages());
    return "product-info";
  }

  @PostMapping("/add")
  public String create(@ModelAttribute ProductDTO productDto, Principal principal) {
    productServiceDefault.create(productServiceDefault.generateProduct(productDto, principal));
    return "redirect:/products";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable("id") long id, Principal principal) {
    Product productById = productServiceDefault.findById(id);
    User owner = productById.getUser();
    if (!owner.getUsername().equals(principal.getName())) {
      throw new IllegalArgumentException("Attempt to remove a product by a user who does not own it ");
    }
    productServiceDefault.deleteById(id);
    return "redirect:/products";
  }
}
