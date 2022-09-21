package com.kazhukov.webshop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {
  private final String GREEN_BOLD_TEXT = "\033[1;32m";
  private final String RESET_TEXT = "\033[0m";

  @Around("Pointcuts.getUserByUsernamePointcut()")
  public Object aroundGetUserByUsernameAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    var getUserResolver = new DefaultAspectArgumentsResolver<>(joinPoint, String.class);
    getUserResolver.resolveArgs(username -> System.out.println(GREEN_BOLD_TEXT
      + "Received user from " + joinPoint.getSignature()
      + " with username: " + username
      + RESET_TEXT)
    );
    return joinPoint.proceed();
  }
}
