package com.kazhukov.webshop.services;

import com.kazhukov.webshop.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
  Image create(Image image);
  List<Image> createImages(List<MultipartFile> multipartFiles);
  Image findById(long id);
}
