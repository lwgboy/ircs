package com.github.hasoo.ircs.core;

import com.github.hasoo.ircs.core.entity.MsgLog;
import com.github.hasoo.ircs.core.repository.MessageLogRepository;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
@ComponentScan("com.github.hasoo.ircs.core.repository")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class JdbcMessageLogRepositoryTest {

  @Autowired
  private MessageLogRepository jdbcMessageLogRepository;

  @Test
  @Rollback(true)
  public void testInsert() {
    try {
      jdbcMessageLogRepository.insert(MsgLog.builder().msgKey("1").status(1).build());
    } catch (DuplicateKeyException e) {

    }

    // @formatter:off
    jdbcMessageLogRepository.update(
        MsgLog.builder()
          .msgKey("1")
          .userKey("1004")
          .groupname("TEST")
          .username("test")
          .resDate(LocalDateTime.now())
          .routeDate(LocalDateTime.now())
          .sentDate(LocalDateTime.now())
          .doneDate(LocalDateTime.now())
          .reportDate(LocalDateTime.now())
          .msgType("SMS")
          .contentType("SMS")
          .phone("01012341234")
          .callback("021231234")
          .message("testmessage")
          .resultCode("100")
          .resultDesc("success")
          .net("SKT")
          .sender("LGT_1")
          .status(1000)
          .build());
    // @formatter:on
  }

}
