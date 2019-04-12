package com.github.hasoo.ircs.auth.service;

import com.github.hasoo.ircs.auth.entity.Account;
import com.github.hasoo.ircs.auth.repository.AccountRepository;
import javax.security.auth.login.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepo;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return accountRepo.findByUsername(s).orElseThrow(
        () -> new UsernameNotFoundException(String.format("Username[%s] not found", s)));
  }

  public Account findAccountByUsername(String username) throws UsernameNotFoundException {
    return accountRepo.findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException(String.format("Username[%s] not found", username)));
  }

  public Account register(Account account) throws AccountException {
    if (accountRepo.countByUsername(account.getUsername()) == 0) {
      account.setPassword(passwordEncoder().encode(account.getPassword()));
      return accountRepo.save(account);
    } else {
      throw new AccountException(
          String.format("Username[%s] already taken.", account.getUsername()));
    }
  }

  @Transactional // To successfully remove the date @Transactional annotation must be added
  public void removeAuthenticatedAccount() throws UsernameNotFoundException {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Account acct = findAccountByUsername(username);
    accountRepo.deleteAccountById(acct.getId());
  }

  @Transactional
  public int updateFee(String username, double fee) {
    return accountRepo.updateAccountSetFee(fee, username);
  }
}
