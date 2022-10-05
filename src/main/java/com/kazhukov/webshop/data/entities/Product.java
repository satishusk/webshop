package com.kazhukov.webshop.data.entities;

import com.kazhukov.webshop.controllers.dtos.ProductDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_generator")
  @SequenceGenerator(name = "product_seq_generator", sequenceName = "product_id_seq")
  @Column(name = "product_id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "price")
  private int price;

  @Column(name = "city")
  private String city;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
  private List<Image> images;

  @Column(name = "date_of_created")
  private LocalDateTime dateOfCreated;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private User user;

  public Product(ProductDTO productDTO, User productOwner) {
    this.title = productDTO.getTitle();
    this.description = productDTO.getDescription();
    this.price = productDTO.getPrice();
    this.city = productDTO.getCity();
    this.images = convertToImages(productDTO.getImageFiles());
    this.dateOfCreated = LocalDateTime.now();
    this.user = productOwner;
  }

  private List<Image> convertToImages(List<? extends MultipartFile> imageFiles) {
    return imageFiles.stream().map(Image::new).toList();
  }

  public void update(Product product) {
    this.title = product.getTitle();
    this.description = product.getDescription();
    this.price = product.getPrice();
    this.city = product.getCity();
    this.images = product.getImages();
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", price=" + price +
        ", city='" + city + '\'' +
        ", dateOfCreated=" + dateOfCreated +
        '}';
  }
}

