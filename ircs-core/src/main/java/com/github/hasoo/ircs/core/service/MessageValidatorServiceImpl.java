package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.callback.CallbackFilter;
import com.github.hasoo.ircs.core.rabbitmq.Publisher;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.hasoo.ircs.core.queue.MessageQue;
import com.github.hasoo.ircs.core.queue.ReportQue;
import com.github.hasoo.ircs.core.spam.SpamFilter;
import com.github.hasoo.ircs.core.util.MessageTransfer;

@Service
@Qualifier("messageValidatorService")
public class MessageValidatorServiceImpl implements MessageValidatorService {
  private static final Logger mlog = LoggerFactory.getLogger("message");

  @Autowired
  CallbackFilter callbackFilter;

  @Autowired
  SpamFilter spamFilter;

  @Autowired
  Publisher publisher;

  @Autowired
  @Qualifier("billingService")
  BillingService billingService;

  public void validateMessage(MessageQue messageQue) {

    boolean blockSpecialCallback = false;

    Character ignoreFlag = callbackFilter.isIgnore(messageQue.getGroupname());
    if ('h' == ignoreFlag) {
      blockSpecialCallback = true;
    }

    if (false == callbackFilter.isComply(messageQue.getCallback(), blockSpecialCallback)) {
      publisher.send(
          new ReportQue(messageQue.getUsername(), messageQue.getMsgKey(), messageQue.getUserKey(),
              messageQue.getPhone(), "3000", "invalid callback", new Date(), "ETC"));
      publisher.send(MessageTransfer.toMsgLogQue(messageQue.getMsgKey(), null, "3000", "세칙위반",
          new Date(), "ETC"));
      mlog.info("BLOCK INVALID CALLBACK " + messageQue.toString());

      return;
    }

    if ('y' != ignoreFlag) {
      if (false == callbackFilter.isWhiteCallback(messageQue.getGroupname(),
          messageQue.getCallback())) {
        publisher.send(
            new ReportQue(messageQue.getUsername(), messageQue.getMsgKey(), messageQue.getUserKey(),
                messageQue.getPhone(), "3100", "invalid callback", new Date(), "ETC"));
        publisher.send(MessageTransfer.toMsgLogQue(messageQue.getMsgKey(), null, "3000", "승인안된발신번호",
            new Date(), "ETC"));
        mlog.info("BLOCK DENY CALLBACK " + messageQue.toString());

        return;
      }
    }

    List<String> spamWords =
        spamFilter.isSpamWord(messageQue.getUsername(), messageQue.getMessage());
    if (0 != spamWords.size()) {
      publisher.send(new ReportQue(messageQue.getUsername(), messageQue.getMsgKey(),
          messageQue.getUserKey(), messageQue.getPhone(), "3200", "spam word", new Date(), "ETC"));
      publisher.send(MessageTransfer.toMsgLogQue(messageQue.getMsgKey(), null, "3000", "스팸단어검출",
          new Date(), "ETC"));
      mlog.info("BLOCK SPAM " + messageQue.toString());

      return;
    }

    if (true == spamFilter.isSpamPhone(messageQue.getUsername(), messageQue.getPhone())) {
      publisher.send(new ReportQue(messageQue.getUsername(), messageQue.getMsgKey(),
          messageQue.getUserKey(), messageQue.getPhone(), "3200", "spam phone", new Date(), "ETC"));
      publisher.send(MessageTransfer.toMsgLogQue(messageQue.getMsgKey(), null, "3000", "스팸번호검출",
          new Date(), "ETC"));
      mlog.info("BLOCK SPAM " + messageQue.toString());

      return;
    }

    publisher.send(MessageTransfer.toSenderQue(messageQue));
    billingService.minus(messageQue.getUsername(), messageQue.getFee());
  }
}
