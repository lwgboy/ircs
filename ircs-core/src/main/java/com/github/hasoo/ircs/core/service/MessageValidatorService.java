package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.queue.MessageQue;

public interface MessageValidatorService {
  public void validateMessage(MessageQue messageQue);
}
