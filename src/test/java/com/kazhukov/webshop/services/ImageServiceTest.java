package com.kazhukov.webshop.services;

import com.kazhukov.webshop.asserts.ImageAssert;
import com.kazhukov.webshop.data.entities.Image;
import com.kazhukov.webshop.factories.TestImageFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
class ImageServiceTest {
  private final TestImageFactory testImageFactory;
  private final ImageService imageService;
  private final ImageAssert imageAssert;

  @Autowired
  public ImageServiceTest(TestImageFactory testImageFactory, ImageService imageService, ImageAssert imageAssert) {
    this.testImageFactory = testImageFactory;
    this.imageService = imageService;
    this.imageAssert = imageAssert;
  }

  @Test
  void create() {
    Image generatedImage = testImageFactory.generate();
    Image savedImage = imageService.create(generatedImage);
    imageAssert.assertEqualsImages(generatedImage, savedImage);
  }

  @Test
  void findById() {
    Image generatedImage = testImageFactory.generate();
    imageService.create(generatedImage);
    Image savedImage = imageService.findById(generatedImage.getId());
    imageAssert.assertEqualsImages(generatedImage, savedImage);
  }

  @Test
  void count() {
    long expectedCount = imageService.count();
    List<Image> images = Stream.generate(testImageFactory::generate).limit(10).toList();
    images.forEach(imageService::create);
    Assertions.assertEquals(expectedCount + 10, imageService.count());
  }
}