package com.github.hasoo.ircs.core.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.github.hasoo.ircs.core.queue.MsgLogQue;
import com.github.hasoo.ircs.core.service.MessageLogService;

@Component
public class MsgLogConsumer {
  private static final Logger llog = LoggerFactory.getLogger("logger");

  @Autowired
  @Qualifier("messageLogService")
  private MessageLogService messageLogService;

  // @RabbitListener(queues = {"${rabbitmq.queue.logger}"})
  // public void receiveMsgLogQue(MsgLogQue msgLogQue) {
  // llog.info("<1> " + msgLogQue.toString());
  // messageLogService.save(msgLogQue);
  // }

  @RabbitListener(queues = {"${rabbitmq.queue.logger}"})
  public void receiveMsgLogQue1(MsgLogQue msgLogQue) {
    llog.info("<1> " + msgLogQue.toString());
    messageLogService.save(msgLogQue);
  }

  @RabbitListener(queues = {"${rabbitmq.queue.logger}"})
  public void receiveMsgLogQue2(MsgLogQue msgLogQue) {
    llog.info("<2> " + msgLogQue.toString());
    messageLogService.save(msgLogQue);
  }

  @RabbitListener(queues = {"${rabbitmq.queue.logger}"})
  public void receiveMsgLogQue3(MsgLogQue msgLogQue) {
    llog.info("<3> " + msgLogQue.toString());
    messageLogService.save(msgLogQue);
  }

  @RabbitListener(queues = {"${rabbitmq.queue.logger}"})
  public void receiveMsgLogQue4(MsgLogQue msgLogQue) {
    llog.info("<4> " + msgLogQue.toString());
    messageLogService.save(msgLogQue);
  }

  @RabbitListener(queues = {"${rabbitmq.queue.logger}"})
  public void receiveMsgLogQue5(MsgLogQue msgLogQue) {
    llog.info("<5> " + msgLogQue.toString());
    messageLogService.save(msgLogQue);
  }

  @RabbitListener(queues = {"${rabbitmq.queue.logger}"})
  public void receiveMsgLogQue6(MsgLogQue msgLogQue) {
    llog.info("<6> " + msgLogQue.toString());
    messageLogService.save(msgLogQue);
  }

  @RabbitListener(queues = {"${rabbitmq.queue.logger}"})
  public void receiveMsgLogQue7(MsgLogQue msgLogQue) {
    llog.info("<7> " + msgLogQue.toString());
    messageLogService.save(msgLogQue);
  }

  @RabbitListener(queues = {"${rabbitmq.queue.logger}"})
  public void receiveMsgLogQue8(MsgLogQue msgLogQue) {
    llog.info("<8> " + msgLogQue.toString());
    messageLogService.save(msgLogQue);
  }

  @RabbitListener(queues = {"${rabbitmq.queue.logger}"})
  public void receiveMsgLogQue9(MsgLogQue msgLogQue) {
    llog.info("<9> " + msgLogQue.toString());
    messageLogService.save(msgLogQue);
  }

  @RabbitListener(queues = {"${rabbitmq.queue.logger}"})
  public void receiveMsgLogQue10(MsgLogQue msgLogQue) {
    llog.info("<10> " + msgLogQue.toString());
    messageLogService.save(msgLogQue);
  }
}
