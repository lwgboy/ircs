package com.github.hasoo.ircs.core.billing;

public interface BillingProvider {
  public boolean bill(String username, double fee);
}
