package com.kazhukov.webshop.services;

import com.kazhukov.webshop.data.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
  Image create(Image image);
  Image findById(long id);
  long count();
}
