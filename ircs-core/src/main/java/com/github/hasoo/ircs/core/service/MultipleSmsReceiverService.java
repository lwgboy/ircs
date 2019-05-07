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
        // @formatter:off
        publisher.send(
            toMsgLogQue(
                  msgKey
                , multipleSms.getGroupKey()
                , phone.getKey()
                , groupname
                , username
                , LocalDateTime.now()
                , 0
                , phone.getPhone()
                , multipleSms.getCallback()
                , message
            )
        );
        // @formatter:on
      }

    } catch (Exception e) {
//      log.error(HUtil.getStackTrace(e));
      log.error("", e);
    }
    return false;
  }

  private String convertTemplates(Map<String, String> messageTemplates, String message) {
    for (Map.Entry<String, String> em : messageTemplates.entrySet()) {
      message = message.replace(em.getKey(), em.getValue());
    }
    return message;
  }

  // @formatter:off
  private MsgLogQue toMsgLogQue(
        String msgKey
      , String groupKey
      , String userKey
      , String groupname
      , String username
      , LocalDateTime resDate
      , Integer status
      , String phone
      , String callback
      , String message
  ) {
    return MsgLogQue.builder()
        .msgKey(msgKey)
        .groupKey(groupKey)
        .userKey(userKey)
        .groupname(groupname)
        .username(username)
        .resDate(resDate)
        .status(status)
        .phone(phone)
        .callback(callback)
        .message(message)
        .build();
  }
  // @formatter:on

  // @formatter:off
  private MessageQue toMessageQue(
        String msgKey
      , String groupKey
      , String userKey
      , String groupname
      , String username
      , Double fee
      , LocalDateTime resDate
      , String phone
      , String callback
      , String message
  ) {
    return MessageQue.builder()
          .msgKey(msgKey)
          .groupKey(groupKey)
          .userKey(userKey)
          .groupname(groupname)
          .username(username)
          .fee(fee)
          .resDate(resDate)
          .msgType(MsgType.SMS.getType())
          .contentType(ContentPriceCode.SMS.getCode())
          .phone(phone)
          .callback(callback)
          .message(message)
        .build();
  }
  // @formatter:on
}
