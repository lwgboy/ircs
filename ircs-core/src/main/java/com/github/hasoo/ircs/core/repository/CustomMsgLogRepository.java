package com.github.hasoo.ircs.core.repository;

import com.github.hasoo.ircs.core.entity.MsgLog;

public interface CustomMsgLogRepository {
  public void insert(MsgLog msgLog);

  public int update(MsgLog msgLog);
}
