package com.github.hasoo.ircs.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
// @EnableWebSecurity(debug = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  public UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(userDetailsService);
    return provider;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  // @Bean
  // public UserDetailsService redisUserDetails() {
  // return new RedisUserDetailsService();
  // }

  // @Override
  // protected void configure(AuthenticationManagerBuilder core) throws Exception {
  // core.userDetailsService(redisUserDetails()).passwordEncoder(passwordEncoder);
  // }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off 
    http.authorizeRequests()
        .anyRequest()
        .authenticated()
          .antMatchers("/", "/**").permitAll()
          .antMatchers(HttpMethod.OPTIONS).permitAll()
        .and()
        .httpBasic()
        .and()
        .csrf().disable();
    // @formatter:on
  }
}