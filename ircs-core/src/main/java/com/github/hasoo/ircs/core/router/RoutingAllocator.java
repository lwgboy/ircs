package com.github.hasoo.ircs.core.router;

import com.github.hasoo.ircs.core.queue.SenderQue;

public interface RoutingAllocator {
  public String getRoutingKey(SenderQue que);
}
