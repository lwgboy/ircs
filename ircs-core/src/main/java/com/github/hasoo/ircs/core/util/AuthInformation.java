package com.github.hasoo.ircs.core.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthInformation {
  public static String getUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public static String getGroupname() {
    return (String) Oauth2Util
        .getExtraInfo(SecurityContextHolder.getContext().getAuthentication(), "groupname")
        .orElse("");
  }

  @SuppressWarnings("unchecked")
  public static Map<String, Double> getContentPrices() {
    return (Map<String, Double>) Oauth2Util
        .getExtraInfo(SecurityContextHolder.getContext().getAuthentication(), "contentprice")
        .orElse(new HashMap<String, Long>());
  }
}
