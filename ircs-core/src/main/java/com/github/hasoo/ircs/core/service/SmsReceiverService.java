package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.dto.SmsRequest;
import com.github.hasoo.ircs.core.enums.ContentPriceCode;
import com.github.hasoo.ircs.core.rabbitmq.Publisher;
import com.github.hasoo.ircs.core.util.AuthInformation;
import com.github.hasoo.ircs.core.util.HUtil;
import com.github.hasoo.ircs.core.util.MessageTransfer;
import com.github.hasoo.ircs.core.util.MessageUniqueId;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsReceiverService implements ReceiverService<SmsRequest> {
  private static final Logger mlog = LoggerFactory.getLogger("message");

  private final Publisher publisher;

  public SmsReceiverService(Publisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public boolean receive(SmsRequest t) {
    mlog.info("RECEIVER " + t.toString());

    try {
      String groupname = AuthInformation.getGroupname();
      String username = AuthInformation.getUsername();
      Map<String, Double> contentPrices = AuthInformation.getContentPrices();
      LocalDateTime resDate = LocalDateTime.now();
      String msgKey = MessageUniqueId.get();

      // If not able to send to RabbitMQ, it have to send error response.
      publisher.send(MessageTransfer.toMsgLogQue(t, groupname, username, msgKey, resDate));

      publisher.send(MessageTransfer.toMessageQue(t, groupname, username, contentPrices.get(
          ContentPriceCode.SMS.getCode()),
          msgKey, resDate));

      return true;
    } catch (Exception e) {
      log.error(HUtil.getStackTrace(e));
    }

    return false;
  }

}
