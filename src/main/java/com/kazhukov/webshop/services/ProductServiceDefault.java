package com.kazhukov.webshop.services;

import com.kazhukov.webshop.entities.Product;
import com.kazhukov.webshop.controllers.dtos.ProductDTO;
import com.kazhukov.webshop.exceptions.ProductNotFoundException;
import com.kazhukov.webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ProductServiceDefault implements ProductService{
  private final ProductRepository productRepository;
  private final ImageService imageService;
  private final UserService userService;

  @Autowired
  public ProductServiceDefault(ProductRepository productRepository, ImageService imageService, UserService userService) {
    this.productRepository = productRepository;
    this.imageService = imageService;
    this.userService = userService;
  }

  public Product create(Product product) {
    return productRepository.save(product);
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }

  public Product findById(long id) {
    return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
  }

  public Product update(Product product) {
    productWithIdNotNull(product.getId());
    return productRepository.save(product);
  }

  public void deleteById(long id) {
    productWithIdNotNull(id);
    productRepository.deleteById(id);
  }

  private void productWithIdNotNull(long id) {
    Product createdProduct = productRepository.findById(id).orElse(null);
    if (createdProduct == null) {
      throw new ProductNotFoundException(id);
    }
  }

  public Product generateProduct(ProductDTO productDto, Principal principal) {
    return new Product(
        productDto,
        imageService.createImages(productDto.getMultipartFiles()),
        userService.getUserByUsername(principal.getName())
    );
  }
}
