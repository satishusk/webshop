package com.kazhukov.webshop.data.entities;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.*;

@Entity
@Data
@Table(name = "images")
@NoArgsConstructor
public class Image implements MultipartFile {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_id_generator")
  @SequenceGenerator(name = "images_id_generator", sequenceName = "images_id_seq")
  @Column(name = "image_id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "originalFileName")
  private String originalFileName;

  @Column(name = "size")
  private long size;

  @Column(name = "contentType")
  private String contentType;

  @Lob
  @Column(name = "content")
  private byte[] content;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
  private Product product;

  private boolean isBeenTransfer = false;

  public Image(String name, String originalFileName, long size,
               String contentType, byte[] content, Product product) {
    this.name = name;
    this.originalFileName = originalFileName;
    this.size = size;
    this.contentType = contentType;
    this.content = content;
    this.product = product;
  }

  public Image(MultipartFile multipartFile) {
    this.name = multipartFile.getName();
    this.originalFileName = multipartFile.getOriginalFilename();
    this.size = multipartFile.getSize();
    this.contentType = multipartFile.getContentType();
    try {
      this.content = multipartFile.getBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getOriginalFilename() {
    return originalFileName;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public boolean isEmpty() {
    return content.length == 0;
  }

  @Override
  public long getSize() {
    return size;
  }

  @Override
  public byte[] getBytes() {
    return content;
  }

  @Override
  public InputStream getInputStream() {
    return new ByteArrayInputStream(content);
  }

  @Override
  public void transferTo(File dest) throws IOException, IllegalStateException {
    if (isBeenTransfer) {
      throw new IllegalStateException("File is been transfer");
    }
    try(var bis = new BufferedInputStream(getInputStream());
        var bos = new BufferedOutputStream(new FileOutputStream(dest))) {
      for(byte[] buffer = new byte[1024]; bis.available() > 0; buffer = bis.readNBytes(buffer.length)) {
        bos.write(buffer);
      }
    }
    isBeenTransfer = true;
  }

  @Override
  public String toString() {
    return "Image{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", originalFileName='" + originalFileName + '\'' +
      ", size=" + size +
      ", contentType='" + contentType + '\'' +
      '}';
  }
}
