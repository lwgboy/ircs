package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.repository.MessageLogRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.github.hasoo.ircs.core.entity.MsgLog;
import com.github.hasoo.ircs.core.queue.MsgLogQue;
import com.github.hasoo.ircs.core.util.MessageTransfer;

@Service
@Qualifier("messageLogService")
@Transactional
public class MessageLogServiceImpl implements MessageLogService {

  @Autowired
  private MessageLogRepository jdbcMessageLogRepository;

  @Override
  public void save(MsgLogQue que) {
    MsgLog msgLog = MessageTransfer.toMsgLog(que);

    for (int i = 0; i < 3; i++) {
      if (0 == jdbcMessageLogRepository.update(msgLog)) {
        try {
          jdbcMessageLogRepository.insert(msgLog);
          break;
        } catch (DuplicateKeyException e) {
        }
        continue;
      }
      break;
    }
  }

  // @Autowired
  // @Qualifier("customMsgLogRepository")
  // CustomMsgLogRepository repository;
  //
  // @Override
  // public void save(MsgLogQue que) {
  // for (int i = 0; i < 3; i++) {
  // try {
  // if (0 == repository.update(msgLog)) {
  // repository.insert(msgLog);
  // }
  // break;
  // } catch (DataIntegrityViolationException ex) {
  // }
  // continue;
  // }
  // break;
  // }
}
