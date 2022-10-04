package com.kazhukov.webshop.services;

import com.kazhukov.webshop.data.entities.Product;
import com.kazhukov.webshop.data.exceptions.EntityNotFoundException;
import com.kazhukov.webshop.data.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceDefault implements ProductService{
  private final ProductRepository productRepository;

  @Autowired
  public ProductServiceDefault(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Product create(Product product) {
    return productRepository.save(product);
  }

  @Override
  public List<Product> findAll() {
    return productRepository.findAll();
  }

  @Override
  public Product findById(long id) {
    return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
  }

  @Override
  public Product update(long id, Product product) {
    Product existsProduct = findById(id);
    existsProduct.update(product);
    return existsProduct;
  }

  @Override
  public void deleteById(long id) {
    productExists(id);
    productRepository.deleteById(id);
  }

  @Override
  public long count() {
    return productRepository.count();
  }

  private void productExists(long id) {
    productRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException(id));
  }
}
