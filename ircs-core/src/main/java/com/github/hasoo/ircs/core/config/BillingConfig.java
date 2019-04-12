package com.github.hasoo.ircs.core.config;

import com.github.hasoo.ircs.core.billing.AmqpBillingProvider;
import com.github.hasoo.ircs.core.billing.BillingManager;
import com.github.hasoo.ircs.core.billing.BillingProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BillingConfig {
  @Value("${billing.msec}")
  private int loopMilliSecond;

  @Value("${billing.standardrate}")
  private double standardRatePrice;

  @Bean
  public BillingProvider billingProvider() {
    // return new JpaBillingProvider();
    return new AmqpBillingProvider();
  }

  @Bean
  public BillingManager billingManager() {
    return BillingManager.builder().billingMilliSec(loopMilliSecond).standardRate(standardRatePrice)
        .billingProvider(billingProvider()).build();
  }
}
