package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.repository.AccountRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.hasoo.ircs.core.billing.BillingManager;

@Service
@Qualifier("billingService")
@Transactional
public class BillingServiceImpl implements BillingService {
  @Autowired
  private BillingManager billingManager;

  @Autowired
  AccountRepository accountRepository;

  public void minus(String username, double fee) {
    billingManager.bill(username, fee * -1);
  }

  public void plus(String username, double fee) {
    billingManager.bill(username, fee);
  }

  public void update(String username, double fee) {
    accountRepository.updateAccountSetFee(fee, username);
  }
}
