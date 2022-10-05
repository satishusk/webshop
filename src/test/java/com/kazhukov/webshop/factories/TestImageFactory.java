package com.kazhukov.webshop.factories;

import com.kazhukov.webshop.data.entities.Image;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class TestImageFactory {
  public static final String path ="D:\\webshop\\src\\test\\java\\com\\kazhukov\\webshop\\resources\\test.png";
  private int userGeneratedCount = 0;

  public Image generate() {
    return new Image(new MockMultipartFile("testImage" + userGeneratedCount++, getFileContent()));
  }

  public Image generate(String name) {
    return generate(new MockMultipartFile(name, getFileContent()));
  }

  public Image generate(MultipartFile multipartFile) {
    return new Image(multipartFile);
  }

  private static byte[] getFileContent() {
    try (InputStream is = new FileInputStream(path)) {
      return is.readAllBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
