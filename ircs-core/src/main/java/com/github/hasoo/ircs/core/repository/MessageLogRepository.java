package com.github.hasoo.ircs.core.repository;

import com.github.hasoo.ircs.core.entity.MsgLog;

public interface MessageLogRepository {

  public void insert(MsgLog msgLog);

  public int update(MsgLog msgLog);
}
