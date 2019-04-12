package com.github.hasoo.ircs.core.router;

import com.github.hasoo.ircs.core.router.map.Sender;
import java.util.List;
import com.github.hasoo.ircs.core.enums.RsltCodeType;
import com.github.hasoo.ircs.core.queue.SenderQue;

public class OrderRoutingAllocator implements RoutingAllocator {

  public String getRoutingKey(SenderQue que) {
    Sender sender = null;
    int level = 0;

    List<Sender> senders = que.getSenders();
    int count = senders.size();
    if (0 == count) { // finished
      return null;
    }

    for (int i = 0; i < count; i++) {
      if (level <= senders.get(i).getLevel()) {
        level = senders.get(i).getLevel();
        sender = senders.get(i);
      }
    }

    if (null == que.getCode() || que.getCode().equals(RsltCodeType.INVA.getCode())) {
      senders.remove(sender);
      return sender.getName();
    }

    return null;
  }
}
