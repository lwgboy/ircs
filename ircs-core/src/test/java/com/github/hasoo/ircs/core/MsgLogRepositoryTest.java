package com.github.hasoo.ircs.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.hasoo.ircs.core.repository.MsgLogRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
@ComponentScan("com.github.hasoo.ircs.core.repository")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MsgLogRepositoryTest {
  @Autowired
  private MsgLogRepository repo;

  @Test
  @Rollback(true)
  public void testInsert() {
    // this.repo.insert(MsgLog.builder().msgKey("1").phone("01012341234").status(1).build());
    // System.out.println("------------------>" + this.repo.findById("1").get().getStatus());
    // Assert.assertTrue("01012341234" == this.repo.findById("1").get().getPhone());
  }

  @Test
  @Rollback(true)
  public void testUpdate() {}
}
