package com.github.hasoo.ircs.core.billing;

import com.github.hasoo.ircs.core.rabbitmq.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hasoo.ircs.core.queue.BillingQue;

public class AmqpBillingProvider implements BillingProvider {
  @Autowired
  Publisher publisher;

  @Override
  public boolean bill(String username, double fee) {
    publisher.send(new BillingQue(username, fee));
    return true;
  }
}
