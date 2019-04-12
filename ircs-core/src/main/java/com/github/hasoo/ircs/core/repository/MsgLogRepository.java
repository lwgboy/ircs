package com.github.hasoo.ircs.core.repository;

import org.springframework.data.repository.CrudRepository;
import com.github.hasoo.ircs.core.entity.MsgLog;

public interface MsgLogRepository extends CrudRepository<MsgLog, String>, CustomMsgLogRepository {
}
