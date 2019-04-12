package com.github.hasoo.ircs.core.util;

import java.util.Map;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

public class Oauth2Util {
  @SuppressWarnings("unchecked")
  public static Optional<Object> getExtraInfo(Authentication auth, String key) {
    OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
    Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
    return Optional.ofNullable(details.get(key));
  }
}
