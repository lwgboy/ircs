package com.github.hasoo.ircs.core.util;

import com.github.hasoo.ircs.core.dto.SmsRequest;
import com.github.hasoo.ircs.core.entity.MsgLog;
import com.github.hasoo.ircs.core.queue.MessageQue;
import com.github.hasoo.ircs.core.queue.MsgLogQue;
import com.github.hasoo.ircs.core.queue.SenderQue;
import java.time.LocalDateTime;

public class MessageTransfer {

  public static MsgLogQue toMsgLogQue(SmsRequest smsRequest, String groupname, String username,
      String msgKey, LocalDateTime resDate) {
    // @formatter:off
    return MsgLogQue.builder()
        .msgKey(msgKey)
        .userKey(smsRequest.getKey())
        .groupname(groupname)
        .username(username)
        .resDate(resDate)
        .msgType("SMS")
        .contentType("SMS")
        .phone(smsRequest.getPhone())
        .callback(smsRequest.getCallback())
        .message(smsRequest.getMessage())
        .status(1)
        .build();
    // @formatter:on
  }

  public static MsgLogQue toMsgLogQue(String msgKey, LocalDateTime routeDate,
      LocalDateTime sentDate,
      String routingKey, String code, String desc, LocalDateTime doneDate, String net) {
    // @formatter:off
    return MsgLogQue.builder()
        .msgKey(msgKey)
        .routeDate(routeDate)
        .sentDate(sentDate)
        .resultCode(code)
        .resultDesc(desc)
        .doneDate(doneDate)
        .net(net)
        .sender(routingKey)
        .status(10)
        .build();
    // @formatter:on
  }

  public static MsgLogQue toMsgLogQue(String msgKey, LocalDateTime sentDate, String code,
      String desc,
      LocalDateTime doneDate, String net) {
    // @formatter:off
    return MsgLogQue.builder()
        .msgKey(msgKey)
        .sentDate(sentDate)
        .resultCode(code)
        .resultDesc(desc)
        .doneDate(doneDate)
        .net(net)
        .status(100)
        .build();
    // @formatter:on
  }

  public static MsgLog toMsgLog(MsgLogQue msgLogQue) {
    // @formatter:off
    return new MsgLog(
        msgLogQue.getMsgKey(),
        msgLogQue.getGroupKey(),
        msgLogQue.getUserKey(),
        msgLogQue.getGroupname(),
        msgLogQue.getUsername(),
        msgLogQue.getResDate(),
        msgLogQue.getRouteDate(),
        msgLogQue.getSentDate(),
        msgLogQue.getDoneDate(),
        msgLogQue.getReportDate(),
        msgLogQue.getStatus(),
        msgLogQue.getMsgType(),
        msgLogQue.getContentType(),
        msgLogQue.getPhone(),
        msgLogQue.getCallback(),
        msgLogQue.getMessage(),
        msgLogQue.getResultCode(),
        msgLogQue.getResultDesc(),
        msgLogQue.getNet(),
        msgLogQue.getSender()
        );
    // @formatter:on
  }

  public static MessageQue toMessageQue(SmsRequest r, String groupname, String username, double fee,
      String msgKey, LocalDateTime resDate) {
    // @formatter:off
    return new MessageQue(
        msgKey,
        "",
        r.getKey(),
        groupname,
        username,
        fee, 
        resDate,
        "SMS",
        "SMS",
        r.getPhone(),
        r.getCallback(),
        r.getMessage()
        );
    // @formatter:on
  }

  public static SenderQue toSenderQue(MessageQue messageQue) {
    // @formatter:off

    return new SenderQue(
        messageQue.getMsgKey(),
        messageQue.getUserKey(),
        messageQue.getGroupname(),
        messageQue.getUsername(),
        messageQue.getFee(),
        messageQue.getResDate(),
        null,
        messageQue.getMsgType(),
        messageQue.getContentType(),
        messageQue.getPhone(),
        messageQue.getCallback(),
        messageQue.getMessage(),
        null,
        null,
        null,
        null,
        null,
        null
        );
    // @formatter:on
  }
}
