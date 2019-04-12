package com.github.hasoo.ircs.core.service;

public interface ReceiverService<T> {
  public boolean receive(T t);
}
