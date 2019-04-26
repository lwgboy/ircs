package com.github.hasoo.ircs.core.config;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceConfig extends ResourceServerConfigurerAdapter {

  private final SecretKeyProvider keyProvider;
  private final CustomAccessTokenConverter customAccessTokenConverter;
  @Value("${security.oauth2.resource.id}")
  private String resourceId;

  public ResourceConfig(
      SecretKeyProvider keyProvider, CustomAccessTokenConverter customAccessTokenConverter) {
    this.keyProvider = keyProvider;
    this.customAccessTokenConverter = customAccessTokenConverter;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    try {
      converter.setSigningKey(keyProvider.getKey());
      converter.setAccessTokenConverter(customAccessTokenConverter);
    } catch (KeyStoreException | NoSuchAlgorithmException | IOException
        | UnrecoverableKeyException | CertificateException e) {
      e.printStackTrace();
    }

    return converter;
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId(resourceId);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .requestMatcher(new OAuthRequestedMatcher())
        .anonymous().disable()
        .authorizeRequests()
          .antMatchers(HttpMethod.OPTIONS).permitAll()
          .antMatchers("/api/v1/sms").access("hasAnyRole('USER')")
        ;
    // @formatter:on
  }

  private static class OAuthRequestedMatcher implements RequestMatcher {

    public boolean matches(HttpServletRequest request) {
      String auth = request.getHeader("Authorization");
      boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
      boolean haveAccessToken = request.getParameter("access_token") != null;
      return haveOauth2Token | haveAccessToken;
    }
  }
}
