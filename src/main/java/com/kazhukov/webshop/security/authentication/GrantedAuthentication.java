package com.kazhukov.webshop.security.authentication;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface GrantedAuthentication {
  boolean isAuthorise();
  Collection<? extends GrantedAuthority> getAuthorities();
  String getUsername();
}
