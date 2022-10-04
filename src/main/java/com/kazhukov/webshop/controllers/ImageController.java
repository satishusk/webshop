package com.kazhukov.webshop.controllers;

import com.kazhukov.webshop.data.entities.Image;

import com.kazhukov.webshop.services.ImageServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.Objects;

@RestController
@Transactional
@RequestMapping("/images")
public class ImageController {
  private final ImageServiceDefault imageServiceDefault;

  @Autowired
  public ImageController(ImageServiceDefault imageServiceDefault) {
    this.imageServiceDefault = imageServiceDefault;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getImageById(@PathVariable("id") long id) {
    Image image = imageServiceDefault.findById(id);
    return ResponseEntity.ok()
      .header("fileName", image.getName())
      .contentType(MediaType.valueOf(Objects.requireNonNull(image.getContentType())))
      .contentLength(image.getSize())
      .body(new InputStreamResource(new ByteArrayInputStream(image.getContent())));
  }
}
