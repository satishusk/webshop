package com.kazhukov.webshop.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
  @Pointcut("execution(* com.kazhukov.webshop.services.UserServiceDefault.getUserByUsername(..))")
  public void getUserByUsernamePointcut(){}
}
