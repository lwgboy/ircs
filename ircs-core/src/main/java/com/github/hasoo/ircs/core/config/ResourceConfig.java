package com.github.hasoo.ircs.core.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceConfig extends ResourceServerConfigurerAdapter {

  @Value("${security.oauth2.resource.id}")
  private String resourceId;

  @Autowired
  private SecretKeyProvider keyProvider;

  @Autowired
  private CustomAccessTokenConverter customAccessTokenConverter;

  private JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    try {
      converter.setSigningKey(keyProvider.getKey());
      converter.setAccessTokenConverter(customAccessTokenConverter);
    } catch (URISyntaxException | KeyStoreException | NoSuchAlgorithmException | IOException
        | UnrecoverableKeyException | CertificateException e) {
      e.printStackTrace();
    }

    return converter;
  }

  private TokenEnhancer tokenEnhancer() {
    return new CustomTokenEnhancer();
  }

  private TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  private DefaultTokenServices tokenServices() {
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(new JwtTokenStore(accessTokenConverter()));
    defaultTokenServices.setSupportRefreshToken(true);
    defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
    return defaultTokenServices;
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId(resourceId).tokenServices(tokenServices()).tokenStore(tokenStore());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .requestMatcher(new OAuthRequestedMatcher())
        .anonymous().disable()
        .authorizeRequests()
          .antMatchers(HttpMethod.OPTIONS).permitAll()
          .antMatchers("/hasoo/**").access("hasAnyRole('USE')")
          .antMatchers("/api/v1/sms").access("hasAnyRole('USER')")
          .antMatchers("/api/register").hasAuthority("ROLE_REGISTER");
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
