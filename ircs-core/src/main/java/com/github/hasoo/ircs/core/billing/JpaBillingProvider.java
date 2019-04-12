package com.github.hasoo.ircs.core.billing;

import com.github.hasoo.ircs.core.repository.AccountRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public class JpaBillingProvider implements BillingProvider {

  @Autowired
  AccountRepository accountRepository;

  @Override
  public boolean bill(String username, double fee) {
    log.debug("bill {} {}", username, fee);
    try {
      accountRepository.updateAccountSetFee(fee, username);
    } catch (Exception ex) {
      log.error(ex.getMessage());
      return false;
    }

    return true;
  }

}
