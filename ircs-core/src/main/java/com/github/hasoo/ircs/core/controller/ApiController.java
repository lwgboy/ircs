package com.github.hasoo.ircs.core.controller;

import java.util.Map;
import javax.security.auth.login.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.hasoo.ircs.core.dto.RestResponse;
import com.github.hasoo.ircs.core.entity.Account;
import com.github.hasoo.ircs.core.service.AccountService;

@RestController
public class ApiController {

  @Autowired
  private AccountService accountService;

  @GetMapping("/api/hello")
  public ResponseEntity<?> hello() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
    @SuppressWarnings("unchecked")
    Map<String, Object> additionalInfo = (Map<String, Object>) oauthDetails.getDecodedDetails();

    String msg = String.format("Hello %s", additionalInfo.get("groupname"));
    return new ResponseEntity<Object>(msg, HttpStatus.OK);
  }

  @GetMapping(path = "/api/me", produces = "application/json")
  public Account me() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return accountService.findAccountByUsername(username);
  }

  @PostMapping(path = "/api/register", produces = "application/json")
  public ResponseEntity<?> register(@RequestBody Account account) throws AccountException {
    account.grantAuthority("ROLE_USER");
    return new ResponseEntity<Object>(accountService.register(account), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('USER')")
  @DeleteMapping(path = "/api/user/remove", produces = "application/json")
  public ResponseEntity<?> removeUser() {
    accountService.removeAuthenticatedAccount();
    return new ResponseEntity<Object>(new RestResponse("User removed."), HttpStatus.OK);
  }

  @GetMapping("/hasoo")
  public String whoAreYou() {
    return "Hasoo Kim";
  }
}
