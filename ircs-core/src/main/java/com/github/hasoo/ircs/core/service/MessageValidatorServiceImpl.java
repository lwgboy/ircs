package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.callback.CallbackFilter;
import com.github.hasoo.ircs.core.enums.NetCode;
import com.github.hasoo.ircs.core.enums.ResultCode;
import com.github.hasoo.ircs.core.queue.MessageQue;
import com.github.hasoo.ircs.core.queue.ReportQue;
import com.github.hasoo.ircs.core.rabbitmq.Publisher;
import com.github.hasoo.ircs.core.spam.SpamFilter;
import com.github.hasoo.ircs.core.util.MessageTransfer;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("messageValidatorService")
public class MessageValidatorServiceImpl implements MessageValidatorService {

  final private CallbackFilter callbackFilter;

  final private SpamFilter spamFilter;

  final private Publisher publisher;

  final private BillingService billingService;

  public MessageValidatorServiceImpl(CallbackFilter callbackFilter,
      SpamFilter spamFilter, Publisher publisher,
      @Qualifier("billingService") BillingService billingService) {
    this.callbackFilter = callbackFilter;
    this.spamFilter = spamFilter;
    this.publisher = publisher;
    this.billingService = billingService;
  }

  public void validateMessage(MessageQue messageQue) {
    ResultCode rcCallback = checkCallback(messageQue);
    if (rcCallback != ResultCode.SUCCESS) {
      failure(messageQue, rcCallback);
      return;
    }
    ResultCode rcSpam = checkSpam(messageQue);
    if (rcSpam != ResultCode.SUCCESS) {
      failure(messageQue, rcSpam);
      return;
    }
    route(messageQue);
  }

  private ResultCode checkCallback(MessageQue messageQue) {
    Character ignoreFlag = callbackFilter.isIgnore(messageQue.getGroupname());
    if (!callbackFilter
        .isComply(messageQue.getCallback(), 'h' == ignoreFlag)) {
      return ResultCode.INVALIDCALLBACK;
    }
    if ('y' != ignoreFlag && !callbackFilter.isWhiteCallback(messageQue.getGroupname(),
        messageQue.getCallback())) {
      return ResultCode.DENYCALLBACK;
    }
    return ResultCode.SUCCESS;
  }

  private ResultCode checkSpam(MessageQue messageQue) {
    if (spamFilter.isSpamPhone(messageQue.getUsername(), messageQue.getPhone())) {
      return ResultCode.SPAMPHONE;
    }
    List<String> spamWords =
        spamFilter.isSpamWord(messageQue.getUsername(), messageQue.getMessage());
    if (0 != spamWords.size()) {
      return ResultCode.SPAMMESSAGE;
    }
    return ResultCode.SUCCESS;
  }

  private void failure(MessageQue messageQue, ResultCode resultCode) {
    publisher.send(new ReportQue(messageQue.getUsername(), messageQue.getMsgKey(),
        messageQue.getUserKey(), messageQue.getPhone(), resultCode.getCode(),
        resultCode.getDesc(),
        LocalDateTime.now(),
        NetCode.ETC.getCode()));
    publisher.send(MessageTransfer
        .toMsgLogQue(messageQue.getMsgKey(), null, resultCode.getCode(), resultCode.getDesc(),
            LocalDateTime.now(), NetCode.ETC.getCode()));
  }

  private void route(MessageQue messageQue) {
    publisher.send(MessageTransfer.toSenderQue(messageQue));
    billingService.minus(messageQue.getUsername(), messageQue.getFee());
  }
}
