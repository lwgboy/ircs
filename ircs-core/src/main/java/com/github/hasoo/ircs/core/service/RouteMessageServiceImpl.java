package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.rabbitmq.Publisher;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.hasoo.ircs.core.enums.RsltCodeType;
import com.github.hasoo.ircs.core.queue.ReportQue;
import com.github.hasoo.ircs.core.queue.SenderQue;
import com.github.hasoo.ircs.core.router.Router;
import com.github.hasoo.ircs.core.util.MessageTransfer;

@Service
@Qualifier("routeMessageService")
public class RouteMessageServiceImpl implements RouteMessageService {
  private static final Logger mlog = LoggerFactory.getLogger("message");

  @Value("${rabbitmq.exchange.sender}")
  private String senderExchangeName;

  @Autowired
  Router router;

  @Autowired
  Publisher publisher;

  @Autowired
  @Qualifier("billingService")
  BillingService billingService;

  public void route(SenderQue senderQue) {
    // int senderCount = Optional.ofNullable(senderQue.getSenders()).map(List::size).orElse(0);
    String routingKey = router.route(senderQue);
    if (null == routingKey) {
      finishMessage(senderQue);
    } else {
      routingMessage(senderQue, routingKey);
    }
  }

  private void routingMessage(SenderQue que, String routingKey) {
    if (null == que.getCode()) {
      mlog.info("ROUTE routingKey:{} {}", routingKey, que.toString());
    } else {
      mlog.info("REROUTE routingKey:{} {}", routingKey, que.toString());
    }

    publisher.send(MessageTransfer.toMsgLogQue(que.getMsgKey(), new Date(), que.getSentDate(),
        routingKey, que.getCode(), que.getDesc(), que.getDoneDate(), que.getNet()));

    publisher.send(senderExchangeName, routingKey, que);
  }

  private void finishMessage(SenderQue que) {
    mlog.info("FINISHE {}", que.toString());

    if (true != que.getCode().equals(RsltCodeType.SUCC.getCode())) {
      billingService.plus(que.getUsername(), que.getFee());
    }

    publisher.send(MessageTransfer.toMsgLogQue(que.getMsgKey(), que.getSentDate(), que.getCode(),
        que.getDesc(), que.getDoneDate(), que.getNet()));

    publisher.send(new ReportQue(que.getUsername(), que.getMsgKey(), que.getUserKey(),
        que.getPhone(), que.getCode(), que.getDesc(), que.getDoneDate(), que.getNet()));
  }
}
