package com.github.hasoo.ircs.core.rabbitmq;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.github.hasoo.ircs.core.queue.BillingQue;
import com.github.hasoo.ircs.core.queue.MessageQue;
import com.github.hasoo.ircs.core.queue.MsgLogQue;
import com.github.hasoo.ircs.core.queue.ReportQue;
import com.github.hasoo.ircs.core.queue.SenderQue;

@Component
public class Publisher {
  public static final String DELIVERY_PREFIX = "dr_";

  @Value("${rabbitmq.exchange.logger}")
  private String loggerExchange;

  @Value("${rabbitmq.routingkey.logger}")
  private String loggerRoutingKey;

  @Value("${rabbitmq.exchange.router}")
  private String routerExchange;

  @Value("${rabbitmq.routingkey.router}")
  private String routerRoutingKey;

  @Value("${rabbitmq.exchange.billing}")
  private String billingExchange;

  @Value("${rabbitmq.routingkey.billing}")
  private String billingRoutingKey;

  @Value("${rabbitmq.exchange.validator}")
  private String validatorExchange;

  @Value("${rabbitmq.routingkey.validator}")
  private String validatorRoutingKey;

  @Autowired
  private RabbitAdmin rabbitAdmin;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private DirectExchange reportDirectExchange;

  public <T> void send(String exchange, String routingKey, T t) {
    rabbitTemplate.convertAndSend(exchange, routingKey, t);
  }

  public void send(ReportQue que) {
    rabbitTemplate.convertAndSend(reportDirectExchange.getName(), que.getUsername(), que);
  }

  public void send(MsgLogQue que) {
    rabbitTemplate.convertAndSend(loggerExchange, loggerRoutingKey, que);
  }

  public void send(SenderQue que) {
    rabbitTemplate.convertAndSend(routerExchange, routerRoutingKey, que);
  }

  public void send(MessageQue que) {
    rabbitTemplate.convertAndSend(this.validatorExchange, this.validatorRoutingKey, que);
  }

  public void retrySend(ReportQue que) {
    this.reportUserBinding(que.getUsername());
    this.send(que);
  }

  private void reportUserBinding(String username) {
    Queue queue = new Queue(DELIVERY_PREFIX + username);
    rabbitAdmin.declareQueue(queue);
    rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(reportDirectExchange).with(username));
  }

  public void send(BillingQue que) {
    rabbitTemplate.convertAndSend(billingExchange, billingRoutingKey, que);
  }
}
