package com.kazhukov.webshop.factories;

import com.kazhukov.webshop.controllers.dtos.ProductDTO;
import com.kazhukov.webshop.data.entities.Product;
import com.kazhukov.webshop.data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestProductFactory {
  private final TestUserFactory testUserFactory;
  private final TestImageFactory testImageFactory;
  private int productGeneratedCount = 0;

  @Autowired
  public TestProductFactory(TestUserFactory testUserFactory, TestImageFactory testImageFactory) {
    this.testUserFactory = testUserFactory;
    this.testImageFactory = testImageFactory;
  }

  public Product generate() {
    return generate(testUserFactory.generate());
  }

  public Product generate(User owner) {
    ProductDTO productDTO = new ProductDTO(
      "testProduct" + productGeneratedCount,
      "testDescription" + productGeneratedCount,
      productGeneratedCount,
      "testCity" + productGeneratedCount,
      new ArrayList<>(List.of(
        testImageFactory.generate(),
        testImageFactory.generate()
      ))
    );
    productGeneratedCount++;
    return new Product(productDTO, owner);
  }
}
