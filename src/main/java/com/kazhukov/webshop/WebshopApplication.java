package com.kazhukov.webshop;

import com.kazhukov.webshop.security.authentication.resolvers.GrantedAuthenticationArgumentResolver;
import com.kazhukov.webshop.security.filters.ActiveUserFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
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
