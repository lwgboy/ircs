package com.github.hasoo.ircs.core.billing;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;

public class TimeHashMap<K, E> {

  private HashMap<K, TimeQue<E>> expiringMap = new HashMap<>();

  public void put(K k, E e) {
    expiringMap.put(k, new TimeQue<E>(e, LocalDateTime.now()));
  }

  public Optional<E> get(K k) {
    return Optional.ofNullable(expiringMap.get(k)).map(this::getE);
  }

  public void replace(K k, E e) {
    expiringMap.replace(k, new TimeQue<E>(e, LocalDateTime.now()));
  }

  public void remove(K k) {
    expiringMap.remove(k);
  }

  public void loopElements(CallbackQue<K, E, LocalDateTime> callbackQue) {
    Iterator<Entry<K, TimeQue<E>>> itor = expiringMap.entrySet().iterator();
    while (itor.hasNext()) {
      Entry<K, TimeQue<E>> entry = itor.next();
      TimeQue<E> que = entry.getValue();
      if (callbackQue.pushAfterRemove(entry.getKey(), que.getT(), que.getUpdatedDate())) {
        itor.remove();
      }
    }
  }

  private E getE(TimeQue<E> que) {
    return que.getT();
  }

  @Data
  @AllArgsConstructor
  private class TimeQue<T> {

    private T t;
    private LocalDateTime updatedDate;
  }
}
