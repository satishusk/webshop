package com.kazhukov.webshop.services;

import com.kazhukov.webshop.controllers.dtos.ProductDTO;
import com.kazhukov.webshop.entities.Product;
import com.kazhukov.webshop.exceptions.ProductNotFoundException;

import java.security.Principal;
import java.util.List;

public interface ProductService {
  Product create(Product product);
  List<Product> findAll();
  Product findById(long id);
  Product update(Product product);
  void deleteById(long id);
  Product generateProduct(ProductDTO productDto, Principal principal);
  long count();
}
