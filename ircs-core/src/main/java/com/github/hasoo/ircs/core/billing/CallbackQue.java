package com.github.hasoo.ircs.core.billing;

public interface CallbackQue<K, E, LocalDateTime> {

  public boolean pushAfterRemove(K k, E e, LocalDateTime date);
}
