package com.kazhukov.webshop.services;

import com.kazhukov.webshop.asserts.ProductAssert;
import com.kazhukov.webshop.data.exceptions.EntityNotFoundException;
import com.kazhukov.webshop.data.entities.Product;
import com.kazhukov.webshop.factories.TestProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
class ProductServiceTest {
  private final ProductService productService;
  private final TestProductFactory testProductFactory;
  private final ProductAssert productAssert;

  @Autowired
  public ProductServiceTest(ProductService productService, TestProductFactory testProductFactory, ProductAssert productAssert) {
    this.productService = productService;
    this.testProductFactory = testProductFactory;
    this.productAssert = productAssert;
  }

  @Test
  void create() {
    Product expected = testProductFactory.generate();
    Product actual = productService.create(expected);
    productAssert.assertEqualsProducts(expected, actual);
  }

  @Test
  void findAll() {
    long productsCountBefore = productService.count();
    List<Product> createdProducts = new ArrayList<>();
    createdProducts.add(productService.create(testProductFactory.generate()));
    createdProducts.add(productService.create(testProductFactory.generate()));

    List<Product> all = productService.findAll();
    Map<Long, Product> productMap = all.stream().collect(Collectors.toMap(Product::getId, p -> p));

    Assertions.assertEquals(2, all.size() - productsCountBefore);
    createdProducts.forEach(product -> {
      Assertions.assertTrue(productMap.containsKey(product.getId()));
      Product mappedProduct = productMap.get(product.getId());
      productAssert.assertEqualsProducts(product, mappedProduct);
    });
  }

  @Test
  void findById() {
    Product presentProduct = productService.create(testProductFactory.generate());
    Product productById = productService.findById(presentProduct.getId());
    productAssert.assertEqualsProducts(presentProduct, productById);
  }

  @Test
  void update() {

    Product savedProduct = productService.create(testProductFactory.generate());
    long countBefore = productService.count();
    Product editedProduct = productService.update(savedProduct.getId(), testProductFactory.generate());
    long countAfter = productService.count();
    Product presentProduct = productService.findById(savedProduct.getId());

    Assertions.assertEquals(countBefore, countAfter);
    productAssert.assertEqualsProducts(savedProduct, editedProduct);
    productAssert.assertEqualsProducts(editedProduct, presentProduct);
  }

  @Test
  void deleteById() {
    Product product = productService.create(testProductFactory.generate());

    long productId = product.getId();
    long productCountBefore = productService.count();
    productService.deleteById(productId);
    long productCountAfter = productService.count();

    Assertions.assertEquals(productCountBefore - 1, productCountAfter);
    Assertions.assertThrows(EntityNotFoundException.class, () -> productService.findById(productId));
  }

  @Test
  void count() {
    long countBefore = productService.count();
    productService.create(testProductFactory.generate());
    long countAfter = productService.count();

    Assertions.assertEquals(countBefore + 1, countAfter);
  }
}