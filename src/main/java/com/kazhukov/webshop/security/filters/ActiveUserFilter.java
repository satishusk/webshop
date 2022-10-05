package com.kazhukov.webshop.security.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@Component
@RequiredArgsConstructor
public class ActiveUserFilter implements Filter {
  private final UserDetailsService userDetailsService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    Principal userPrincipal = httpServletRequest.getUserPrincipal();
    if (userPrincipal == null
      || userDetailsService.loadUserByUsername(userPrincipal.getName()).isEnabled()) {
      chain.doFilter(request, response);
    } else {
      SecurityContext context = SecurityContextHolder.getContext();
      Authentication prevAuth = context.getAuthentication();
      context.setAuthentication(new AnonymousAuthenticationToken(
        prevAuth.getName(), prevAuth.getPrincipal(), prevAuth.getAuthorities())
      );
      throw new AccessDeniedException("Access denied to user with name: " + prevAuth.getName());
    }
  }
}
