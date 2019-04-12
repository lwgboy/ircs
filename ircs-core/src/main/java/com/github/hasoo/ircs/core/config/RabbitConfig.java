package com.github.hasoo.ircs.core.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  @Value("${rabbitmq.queue.billing}")
  private String billingQueueName;

  @Value("${rabbitmq.exchange.billing}")
  private String billingExchangeName;

  @Value("${rabbitmq.routingkey.billing}")
  private String billingRoutingkeyName;

  @Value("${rabbitmq.queue.logger}")
  private String loggerQueueName;

  @Value("${rabbitmq.exchange.logger}")
  private String loggerExchangeName;

  @Value("${rabbitmq.routingkey.logger}")
  private String loggerRoutingkeyName;

  @Value("${rabbitmq.queue.validator}")
  private String validatorQueueName;

  @Value("${rabbitmq.exchange.validator}")
  private String validatorExchangeName;

  @Value("${rabbitmq.routingkey.validator}")
  private String validatorRoutingkeyName;

  @Value("${rabbitmq.queue.router}")
  private String routerQueueName;

  @Value("${rabbitmq.exchange.router}")
  private String routerExchangeName;

  @Value("${rabbitmq.routingkey.router}")
  private String routerRoutingkeyName;

  @Value("${rabbitmq.queue.alternate.report}")
  private String alternateReportQueueName;

  @Value("${rabbitmq.exchange.alternate.report}")
  private String alternateReportExchangeName;

  @Value("${rabbitmq.exchange.report}")
  private String reportExchangeName;

  @Value("${rabbitmq.exchange.sender}")
  private String senderExchangeName;

  @Bean
  public Jackson2JsonMessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public Queue billingQueue() {
    return new Queue(billingQueueName);
  }

  @Bean
  public Queue loggerQueue() {
    return new Queue(loggerQueueName);
  }

  @Bean
  public Queue validatorQueue() {
    return new Queue(validatorQueueName);
  }

  @Bean
  public Queue routerQueue() {
    return new Queue(routerQueueName);
  }

  @Bean
  public Queue alternateReportQueue() {
    return new Queue(alternateReportQueueName);
  }

  @Bean
  public DirectExchange billingDirectExchange() {
    return new DirectExchange(billingExchangeName);
  }

  @Bean
  public DirectExchange loggerDirectExchange() {
    return new DirectExchange(loggerExchangeName);
  }

  @Bean
  public DirectExchange validatorDirectExchange() {
    return new DirectExchange(validatorExchangeName);
  }

  @Bean
  public DirectExchange routerDirectExchange() {
    return new DirectExchange(routerExchangeName);
  }

  @Bean
  public FanoutExchange alternateReportFanoutExchange() {
    return new FanoutExchange(alternateReportExchangeName);
  }

  @Bean
  public DirectExchange reportDirectExchange() {
    Map<String, Object> args = new HashMap<>();
    args.put("alternate-exchange", alternateReportExchangeName);
    return new DirectExchange(reportExchangeName, true, false, args);
  }

  @Bean
  public DirectExchange senderDirectExchange() {
    // Map<String, Object> args = new HashMap<>();
    // args.put("alternate-exchange", alternateReportExchangeName);
    // return new DirectExchange(reportExchangeName, true, false, args);
    return new DirectExchange(senderExchangeName);
  }

  @Bean
  public Binding billingBinding(Queue billingQueue, DirectExchange billingDirectExchange) {
    return BindingBuilder.bind(billingQueue).to(billingDirectExchange).with(billingRoutingkeyName);
  }

  @Bean
  public Binding loggerBinding(Queue loggerQueue, DirectExchange loggerDirectExchange) {
    return BindingBuilder.bind(loggerQueue).to(loggerDirectExchange).with(loggerRoutingkeyName);
  }

  @Bean
  public Binding validatorBinding(Queue validatorQueue, DirectExchange validatorDirectExchange) {
    return BindingBuilder.bind(validatorQueue).to(validatorDirectExchange)
        .with(validatorRoutingkeyName);
  }

  @Bean
  public Binding routerBinding(Queue routerQueue, DirectExchange routerDirectExchange) {
    return BindingBuilder.bind(routerQueue).to(routerDirectExchange).with(routerRoutingkeyName);
  }

  @Bean
  public Binding alternateReportBinding(Queue alternateReportQueue,
      FanoutExchange alternateReportFanoutExchange) {
    return BindingBuilder.bind(alternateReportQueue).to(alternateReportFanoutExchange);
  }
}
