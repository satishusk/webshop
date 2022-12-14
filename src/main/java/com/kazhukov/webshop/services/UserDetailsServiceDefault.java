package com.kazhukov.webshop.services;

import com.kazhukov.webshop.data.entities.Role;
import com.kazhukov.webshop.data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserDetailsServiceDefault implements UserDetailsService {
  private final UserService userService;

  @Autowired
  public UserDetailsServiceDefault(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User userByUsername = userService.getUserByUsername(username);
    return new org.springframework.security.core.userdetails.User(
      username,
      userByUsername.getPasswordHash(),
      userByUsername.isActive(),
      true,
      true,
      true,
      convertRoles(userByUsername.getRoles())
    );
  }

  private Collection<GrantedAuthority> convertRoles(Collection<Role> roles) {
    return roles.stream()
      .map(role -> new SimpleGrantedAuthority(role.getName()))
      .collect(Collectors.toList());
  }
}
