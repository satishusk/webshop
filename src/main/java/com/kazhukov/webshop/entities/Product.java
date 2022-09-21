package com.kazhukov.webshop.entities;

import com.kazhukov.webshop.controllers.dtos.ProductDTO;
import com.kazhukov.webshop.exceptions.TransientEntityException;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @Column(name = "preview_image_id")
  private long previewImageId;

  @Column(name = "date_of_created")
  private LocalDateTime dateOfCreated;

  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private User user;

  public Product(ProductDTO productDto, List<Image> images, User productOwner) {
    this.title = productDto.getTitle();
    this.description = productDto.getDescription();
    this.price = productDto.getPrice();
    this.city = productDto.getCity();
    this.images = requiresNotTransientImages(establishReferenceToProduct(images));
    this.previewImageId = images.get(0).getId();
    this.dateOfCreated = LocalDateTime.now();
    this.user = productOwner;
  }

  private List<Image> requiresNotTransientImages(List<Image> images) {
    List<Image> supposedlyTransientImages = findAnyTransientImage(images);
    if (supposedlyTransientImages.size() != 0) {
      throw new TransientEntityException(supposedlyTransientImages);
    }
    return images;
  }

  private List<Image> findAnyTransientImage(List<Image> images) {
    return images.stream()
        .filter(image -> image.getId() == null)
        .toList();
  }

  private List<Image> establishReferenceToProduct(List<Image> images) {
    return images.stream().peek(image -> image.setProduct(this)).toList();
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", price=" + price +
        ", city='" + city + '\'' +
        ", previewImageId=" + previewImageId +
        ", dateOfCreated=" + dateOfCreated +
        '}';
  }
}

