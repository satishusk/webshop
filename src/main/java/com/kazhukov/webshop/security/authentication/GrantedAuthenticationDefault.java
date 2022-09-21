package com.kazhukov.webshop.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class GrantedAuthenticationDefault implements GrantedAuthentication{
  private final boolean isAuthenticated;
  private final Authentication authentication;

  public GrantedAuthenticationDefault(boolean isAuthenticated, Authentication authentication) {
    this.isAuthenticated = isAuthenticated;
    this.authentication = authentication;
  }

  @Override
  public boolean isAuthorise() {
    return isAuthenticated;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authentication.getAuthorities();
  }

  @Override
  public String getUsername() {
    return authentication.getName();
  }
}
