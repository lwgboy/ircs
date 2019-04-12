package com.github.hasoo.ircs.core.service;

import java.util.List;
import com.github.hasoo.ircs.core.queue.ReportQue;

public interface ReportDeliverService {
  List<ReportQue> receiveReport(String username);
}
