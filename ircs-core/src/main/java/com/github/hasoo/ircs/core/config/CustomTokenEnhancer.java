package com.github.hasoo.ircs.core.config;

import com.github.hasoo.ircs.core.entity.Account;
import com.github.hasoo.ircs.core.entity.ContentPrice;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class CustomTokenEnhancer implements TokenEnhancer {

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {

    Account user = (Account) authentication.getPrincipal();

    HashMap<String, Double> prices = new HashMap<>();

    Map<String, ContentPrice> contentPrices = user.getContentPrices();
    for (Map.Entry<String, ContentPrice> em : contentPrices.entrySet()) {
      prices.put(em.getKey(), em.getValue().getPrice());
    }

    Map<String, Object> additionalInfo = new HashMap<>();
    additionalInfo.put("groupname", user.getGroupname());
    additionalInfo.put("contentprice", prices);
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    return accessToken;
  }

}
