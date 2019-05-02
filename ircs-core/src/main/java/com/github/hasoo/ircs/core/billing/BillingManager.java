package com.github.hasoo.ircs.core.billing;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BillingManager {

  private TimeHashMap<String, Double> timeHashMap = new TimeHashMap<>();

  private BillingProvider billingProvider;
  private int billingMilliSec;
  private double standardRate;

  private BillingManager(BillingManagerBuilder builder) {
    this.billingProvider = builder.billingProvider;
    this.billingMilliSec = builder.billingMilliSec;
    this.standardRate = builder.standardRate;
  }

  public static BillingManagerBuilder builder() {
    return new BillingManagerBuilder();
  }

  public synchronized void bill(String username, double fee) {
    Double userFee = timeHashMap.get(username).map(obj -> {
      BigDecimal savedFee = BigDecimal.valueOf(obj);
      BigDecimal inputFee = BigDecimal.valueOf(fee);
      return savedFee.add(inputFee).doubleValue();
    }).orElse(fee);

    timeHashMap.put(username, userFee);
  }

  public synchronized void perform() {
    CallbackQue<String, Double, LocalDateTime> callbackQue = (username, fee, date) -> {
      if ((standardRate * -1 >= fee) || (standardRate <= fee)
          || (billingMilliSec <= (Instant.now().toEpochMilli() - date.atZone(ZoneId.systemDefault())
          .toInstant().toEpochMilli()))) {
        if (0 == fee) {
          return false;
        }
        log.debug("billing {} {}", username, fee);
        billingProvider.bill(username, fee);
        return true;
      }

      return false;
    };

    timeHashMap.loopElements(callbackQue);
  }

  public static class BillingManagerBuilder {

    private BillingProvider billingProvider;
    private int billingMilliSec = 1000;
    private double standardRate = 100;

    public BillingManagerBuilder billingProvider(BillingProvider billingProvider) {
      this.billingProvider = billingProvider;
      return this;
    }

    public BillingManagerBuilder billingMilliSec(int billingMilliSec) {
      this.billingMilliSec = billingMilliSec;
      return this;
    }

    public BillingManagerBuilder standardRate(double standardRate) {
      this.standardRate = standardRate;
      return this;
    }

    public BillingManager build() {
      return new BillingManager(this);
    }
  }
}
