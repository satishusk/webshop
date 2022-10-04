package com.kazhukov.webshop.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
  private String title;
  private String description;
  private int price;
  private String city;
  private List<? extends MultipartFile> imageFiles;
}
