package com.github.hasoo.ircs.auth.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  @Value("${security.oauth2.resource.id}")
  private String resourceId;

  @Value("${access_token.validity_period}")
  private int accessTokenValiditySeconds;

  @Value("${refresh_token.validity_period}")
  private int refreshTokenValiditySeconds;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private SecretKeyProvider keyProvider;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private CustomAccessTokenConverter customAccessTokenConverter;

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
    } catch (URISyntaxException | KeyStoreException | NoSuchAlgorithmException | IOException
        | UnrecoverableKeyException | CertificateException e) {
      e.printStackTrace();
    }

    return converter;
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setSupportRefreshToken(true);
    defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
    return defaultTokenServices;
  }

  @Bean
  public TokenEnhancer tokenEnhancer() {
    return new CustomTokenEnhancer();
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(this.authenticationManager).tokenServices(tokenServices());
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
        .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // @formatter:off
    clients
      .inMemory()
        .withClient("normal-app")
          .authorizedGrantTypes("authorization_code", "implicit")
          .authorities("ROLE_CLIENT")
          .scopes("read", "write")
          .resourceIds(resourceId)
          .accessTokenValiditySeconds(accessTokenValiditySeconds)
          .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
          .and()
        .withClient("trusted-app")
          .authorizedGrantTypes("client_credentials", "password", "refresh_token")
          .authorities("ROLE_TRUSTED_CLIENT")
          .scopes("read", "write")
          .resourceIds(resourceId)
          .accessTokenValiditySeconds(accessTokenValiditySeconds)
          .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
          .secret(passwordEncoder.encode("secret"))
          .and()
        .withClient("register-app")
          .authorizedGrantTypes("client_credentials")
          .authorities("ROLE_REGISTER")
          .scopes("read")
          .resourceIds(resourceId)
          .secret(passwordEncoder.encode("secret"))
          .and()
        .withClient("my-client-with-registered-redirect")
          .authorizedGrantTypes("authorization_code")
          .authorities("ROLE_CLIENT")
          .scopes("read", "trust")
          .resourceIds("oauth2-resource")
          .redirectUris("http://anywhere?key=value")
          ;
     // @formatter:on
  }
}
