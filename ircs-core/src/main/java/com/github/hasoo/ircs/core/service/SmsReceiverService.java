package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.rabbitmq.Publisher;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.hasoo.ircs.core.dto.SmsRequest;
import com.github.hasoo.ircs.core.util.AuthInformation;
import com.github.hasoo.ircs.core.util.HUtil;
import com.github.hasoo.ircs.core.util.MessageTransfer;
import com.github.hasoo.ircs.core.util.MessageUniqueId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsReceiverService implements ReceiverService<SmsRequest> {
  private static final Logger mlog = LoggerFactory.getLogger("message");

  @Autowired
  private Publisher publisher;

  @Override
  public boolean receive(SmsRequest t) {
    mlog.info("RECEIVER " + t.toString());

    try {
      String groupname = AuthInformation.getGroupname();
      String username = AuthInformation.getUsername();
      Map<String, Double> contentPrices = AuthInformation.getContentPrices();
      Date resDate = new Date();
      String msgKey = MessageUniqueId.get();

      // If not able to send to RabbitMQ, it have to send error response.
      publisher.send(MessageTransfer.toMsgLogQue(t, groupname, username, msgKey, resDate));
      publisher.send(MessageTransfer.toMessageQue(t, groupname, username, contentPrices.get("SMS"),
          msgKey, resDate));

      return true;
    } catch (Exception e) {
      log.error(HUtil.getStackTrace(e));
    }

    return false;
  }

}
