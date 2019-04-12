package com.github.hasoo.ircs.core.rabbitmq;

import java.io.IOException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hasoo.ircs.core.queue.ReportQue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Deliver {
  private ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public ReportQue receive(String queueName) {
    ReportQue reportQue = null;
    try {
      String deliveryJson = mapper.writeValueAsString(rabbitTemplate.receiveAndConvert(queueName));
      reportQue = mapper.readValue(deliveryJson, ReportQue.class);
    } catch (IOException e) {
      log.error(e.getMessage());
    }

    return reportQue;
  }
}
