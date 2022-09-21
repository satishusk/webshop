package com.kazhukov.webshop.aop;

import org.aspectj.lang.JoinPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class DefaultAspectArgumentsResolver<T> implements AspectArgumentsResolver<T>{
  private final JoinPoint joinPoint;
  private final Class<T> type;

  public DefaultAspectArgumentsResolver(JoinPoint joinPoint, Class<T> type) {
    this.joinPoint = joinPoint;
    this.type = type;
  }

  public void resolveArgs(Consumer<T> consumer) {
    Object[] args = joinPoint.getArgs();
    for (Object arg : args) {
      if (argImplementsInterface(arg, type)) {
        consumer.accept(type.cast(arg));
      }
    }
  }

  private boolean argImplementsInterface(Object arg, Class<T> type) {
    List<Class<?>> classes = new ArrayList<>(Arrays.asList(arg.getClass().getInterfaces()));
    classes.add(arg.getClass());
    return classes.contains(type);
  }
}