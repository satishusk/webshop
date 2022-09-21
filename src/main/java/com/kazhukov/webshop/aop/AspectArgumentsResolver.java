package com.kazhukov.webshop.aop;

import java.util.function.Consumer;

public interface AspectArgumentsResolver<T> {
  void resolveArgs(Consumer<T> consumer);
}
