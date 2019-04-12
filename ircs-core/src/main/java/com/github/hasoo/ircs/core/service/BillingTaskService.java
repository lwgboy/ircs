package com.github.hasoo.ircs.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.github.hasoo.ircs.core.billing.BillingManager;

@Component
public class BillingTaskService {
  @Autowired
  BillingManager billingManager;

  @Scheduled(initialDelay = 1000, fixedRate = 1000)
  public void performBilling() {
    billingManager.perform();
  }

}
