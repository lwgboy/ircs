package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.queue.MsgLogQue;

public interface MessageLogService {
  public void save(MsgLogQue que);
}
