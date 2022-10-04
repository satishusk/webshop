package com.kazhukov.webshop.controllers;

import com.kazhukov.webshop.data.entities.Product;
import com.kazhukov.webshop.data.entities.Role;
import com.kazhukov.webshop.data.entities.User;
import com.kazhukov.webshop.controllers.dtos.ProductDTO;
import com.kazhukov.webshop.data.exceptions.EntityNotFoundException;
import com.kazhukov.webshop.security.authentication.GrantedAuthentication;
import com.kazhukov.webshop.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
  private final ProductService productService;
  private final RoleService roleService;
  private final UserService userService;

  @GetMapping
  public String findAll(Model model, GrantedAuthentication authentication) {
    model.addAttribute("products", productService.findAll());
    model.addAttribute("authentication", authentication);
    model.addAttribute("isAdmin", isAdminAuthenticate(authentication));
    return "products";
  }

  private boolean isAdminAuthenticate(GrantedAuthentication authentication) {
    Role adminRole = roleService.findByName("ROLE_ADMIN")
      .orElseThrow(() -> new EntityNotFoundException("ROLE_ADMIN not found!"));
    return roleService.isPresentRoleInAuthorities(authentication.getAuthorities(), adminRole);
  }

  @GetMapping("/{id}")
  public String findById(@PathVariable("id") long id, Model model) {
    Product product = productService.findById(id);
    model.addAttribute("product", product);
    model.addAttribute("images", product.getImages());
    return "product-info";
  }

  @PostMapping("/add")
  public String create(@ModelAttribute ProductDTO productDto, Principal principal) {
    Product product = new Product(productDto, userService.getUserByUsername(principal.getName()));
    productService.create(product);
    return "redirect:/products";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable("id") long id, Principal principal) {
    Product productById = productService.findById(id);
    User owner = productById.getUser();
    if (!owner.getUsername().equals(principal.getName())) {
      throw new IllegalArgumentException("Attempt to remove a product by a user who does not own it ");
    }
    productService.deleteById(id);
    return "redirect:/products";
  }
}
