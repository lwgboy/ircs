package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.rabbitmq.Deliver;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.hasoo.ircs.core.queue.ReportQue;

@Service
@Qualifier("reportDeliverService")
public class ReportDeliverServiceImpl implements ReportDeliverService {
  public static final String DELIVERY_PREFIX = "dr_";
  @Autowired
  private Deliver receiver;

  @Override
  public List<ReportQue> receiveReport(String username) {
    List<ReportQue> reports = null;
    for (int i = 0; i < 100; i++) {
      ReportQue reportQue = receiver.receive(DELIVERY_PREFIX + username);
      if (null == reportQue) {
        break;
      }
      if (null == reports) {
        reports = new ArrayList<>();
      }
      reports.add(reportQue);
    }
    return reports;
  }
}
