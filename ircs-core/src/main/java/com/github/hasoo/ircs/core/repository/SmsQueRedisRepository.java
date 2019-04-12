package com.github.hasoo.ircs.core.repository;

import org.springframework.data.repository.CrudRepository;
import com.github.hasoo.ircs.core.queue.SmsQue;

public interface SmsQueRedisRepository extends CrudRepository<SmsQue, String> {

}
