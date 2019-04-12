package com.github.hasoo.ircs.core.service;

import com.github.hasoo.ircs.core.repository.UserRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RedisUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRedisRepository userRedisRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return userRedisRepository.findById(username).orElseThrow(
        () -> new UsernameNotFoundException(String.format("Unknown username:%s", username)));
  }
}
