package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.dto.MultipleSms;
import com.github.hasoo.ircs.core.dto.Phone;
import com.github.hasoo.ircs.core.enums.ContentPriceCode;
import com.github.hasoo.ircs.core.enums.MsgType;
import com.github.hasoo.ircs.core.queue.MessageQue;
import com.github.hasoo.ircs.core.queue.MsgLogQue;
import com.github.hasoo.ircs.core.rabbitmq.Publisher;
import com.github.hasoo.ircs.core.util.AuthInformation;
import com.github.hasoo.ircs.core.util.MessageUniqueId;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MultipleSmsReceiverService implements ReceiverService<MultipleSms> {

  private Publisher publisher;

  public MultipleSmsReceiverService(Publisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public boolean receive(MultipleSms multipleSms) {
    try {
      String groupname = AuthInformation.getGroupname();
      String username = AuthInformation.getUsername();
      Map<String, Double> contentPrices = AuthInformation.getContentPrices();
      LocalDateTime resDate = LocalDateTime.now();
      String msgKey = MessageUniqueId.get();

      List<Phone> phones = multipleSms.getPhones();

      for (Phone phone : phones) {
        String message = convertTemplates(phone.getTemplates(), multipleSms.getMessage());
        publisher.send(
            MsgLogQue.builder()
                .msgKey(msgKey)
                .groupKey(multipleSms.getGroupKey())
                .userKey(phone.getKey())
                .groupname(groupname)
                .username(username)
                .resDate(LocalDateTime.now())
                .status(1)
                .phone(phone.getPhone())
                .callback(multipleSms.getCallback())
                .message(message)
                .build()
        );

        publisher.send(
            MessageQue.builder()
                .msgKey(msgKey)
                .groupKey(multipleSms.getGroupKey())
                .userKey(phone.getKey())
                .groupname(groupname)
                .username(username)
                .fee(contentPrices.get(ContentPriceCode.SMS.getCode()))
                .resDate(resDate)
                .msgType(MsgType.SMS.getType())
                .contentType(ContentPriceCode.SMS.getCode())
                .phone(phone.getPhone())
                .callback(multipleSms.getCallback())
                .message(message)
                .build()
        );
      }
    } catch (Exception e) {
//      log.error(HUtil.getStackTrace(e));
      log.error("", e);
      return false;
    }
    return true;
  }

  private String convertTemplates(Map<String, String> messageTemplates, String message) {
    for (Map.Entry<String, String> em : messageTemplates.entrySet()) {
      message = message.replace(em.getKey(), em.getValue());
    }
    return message;
  }
}
