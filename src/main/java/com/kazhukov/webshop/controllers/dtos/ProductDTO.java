package com.kazhukov.webshop.controllers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDTO {
  private String title;
  private String description;
  private int price;
  private String city;
  private List<MultipartFile> multipartFiles;

  public ProductDTO(String title, String description, int price,
                    String city, List<MultipartFile> multipartFiles) {
    this.title = title;
    this.description = description;
    this.price = price;
    this.city = city;
    this.multipartFiles = multipartFiles;
  }
}
