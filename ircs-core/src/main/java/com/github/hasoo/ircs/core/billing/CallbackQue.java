package com.github.hasoo.ircs.core.billing;

public interface CallbackQue<K, E, Date> {
  public boolean pushAfterRemove(K k, E e, Date date);
}
