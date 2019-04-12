package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.queue.SenderQue;

public interface RouteMessageService {
  public void route(SenderQue senderQue);
}
