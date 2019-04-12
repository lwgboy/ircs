package com.github.hasoo.ircs.core.billing;

import java.math.BigDecimal;
import java.util.Date;
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

  public synchronized void bill(String username, double fee) {
    Double userFee = timeHashMap.get(username).map(obj -> {
      BigDecimal savedFee = BigDecimal.valueOf(obj);
      BigDecimal inputFee = BigDecimal.valueOf(fee);
      return savedFee.add(inputFee).doubleValue();
    }).orElse(fee);

    timeHashMap.put(username, userFee);
  }

  public synchronized void perform() {
    CallbackQue<String, Double, Date> callbackQue = new CallbackQue<String, Double, Date>() {
      public boolean pushAfterRemove(String username, Double fee, Date date) {
        if ((standardRate * -1 >= fee) || (standardRate <= fee)
            || (billingMilliSec <= (new Date().getTime() - date.getTime()))) {
          if (0 == fee.doubleValue()) {
            return false;
          }
          log.debug("billing {} {}", username, fee);
          billingProvider.bill(username, fee.doubleValue());
          return true;
        }

        return false;
      }
    };

    timeHashMap.loopElements(callbackQue);
  }

  public static BillingManagerBuilder builder() {
    return new BillingManagerBuilder();
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
