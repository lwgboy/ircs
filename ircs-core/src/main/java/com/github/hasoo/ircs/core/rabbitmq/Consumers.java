package com.github.hasoo.ircs.core.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.github.hasoo.ircs.core.queue.BillingQue;
import com.github.hasoo.ircs.core.queue.MessageQue;
import com.github.hasoo.ircs.core.queue.ReportQue;
import com.github.hasoo.ircs.core.queue.SenderQue;
import com.github.hasoo.ircs.core.service.BillingService;
import com.github.hasoo.ircs.core.service.MessageValidatorService;
import com.github.hasoo.ircs.core.service.RouteMessageService;

@Component
@RabbitListener(queues = {"${rabbitmq.queue.billing}", "${rabbitmq.queue.router}",
    "${rabbitmq.queue.alternate.report}", "${rabbitmq.queue.validator}"})
public class Consumers {
  private static final Logger blog = LoggerFactory.getLogger("biller");

  @Autowired
  @Qualifier("billingService")
  private BillingService billingService;

  @Autowired
  @Qualifier("routeMessageService")
  private RouteMessageService routeMessageService;

  @Autowired
  @Qualifier("messageValidatorService")
  private MessageValidatorService messageValidatorService;

  @Autowired
  Publisher publisher;

  @RabbitHandler
  public void receive(BillingQue billingQue) {
    blog.info(billingQue.toString());
    billingService.update(billingQue.getUsername(), billingQue.getFee());
  }

  @RabbitHandler
  public void receive(ReportQue reportQue) {
    publisher.retrySend(reportQue);
  }

  @RabbitHandler
  public void receive(SenderQue senderQue) {
    routeMessageService.route(senderQue);
  }

  @RabbitHandler
  public void receive(MessageQue messageQue) {
    messageValidatorService.validateMessage(messageQue);
  }
}
