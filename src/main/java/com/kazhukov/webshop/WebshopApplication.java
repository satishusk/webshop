package com.kazhukov.webshop;

import com.kazhukov.webshop.security.authentication.resolvers.GrantedAuthenticationArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class WebshopApplication implements WebMvcConfigurer {
  public static void main(String[] args) {
    SpringApplication.run(WebshopApplication.class, args);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new GrantedAuthenticationArgumentResolver());
  }
}
