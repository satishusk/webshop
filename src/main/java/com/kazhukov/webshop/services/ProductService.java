package com.kazhukov.webshop.services;

import com.kazhukov.webshop.controllers.dtos.ProductDTO;
import com.kazhukov.webshop.data.entities.Product;
import com.kazhukov.webshop.data.entities.User;

import java.security.Principal;
import java.util.List;

public interface ProductService {
  Product create(Product product);
  List<Product> findAll();
  Product findById(long id);
  Product update(long id, Product product);
  void deleteById(long id);
  long count();
}
