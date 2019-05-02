package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.dto.MultipleSms;
import com.github.hasoo.ircs.core.entity.MsgLog;
import com.github.hasoo.ircs.core.enums.ContentPriceCode;
import com.github.hasoo.ircs.core.enums.MsgType;
import com.github.hasoo.ircs.core.queue.MessageQue;
import com.github.hasoo.ircs.core.rabbitmq.Publisher;
import java.time.LocalDateTime;
import java.util.Date;

public class MultipleSmsReceiverService implements ReceiverService<MultipleSms> {

  private Publisher publisher;

  public MultipleSmsReceiverService(Publisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public boolean receive(MultipleSms multipleSms) {
    return false;
  }

  // @formatter:off
  private MsgLog toMsgLog(
        String msgKey
      , String groupKey
      , String userKey
      , String groupname
      , String username
      , Date resDate
      , Integer status
      , String phone
      , String callback
      , String message
  ) {
    return MsgLog.builder().build();
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
