package com.kazhukov.webshop.security.authentication.resolvers;

import com.kazhukov.webshop.security.authentication.GrantedAuthentication;
import com.kazhukov.webshop.security.authentication.GrantedAuthenticationDefault;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;

public class GrantedAuthenticationArgumentResolver implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(GrantedAuthentication.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) {
    Principal userPrincipal = webRequest.getUserPrincipal();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return new GrantedAuthenticationDefault(userPrincipal != null, authentication);
  }
}
