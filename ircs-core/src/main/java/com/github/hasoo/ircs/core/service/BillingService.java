package com.github.hasoo.ircs.core.service;

public interface BillingService {
  public void plus(String username, double fee);

  public void minus(String username, double fee);

  public void update(String username, double fee);
}
