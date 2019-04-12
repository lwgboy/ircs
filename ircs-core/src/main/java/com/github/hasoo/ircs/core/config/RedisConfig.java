package com.github.hasoo.ircs.core.config;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories
public class RedisConfig {

  // @Bean
  // public RedisConnectionFactory redisConnectionFactory() {
  // LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
  // return connectionFactory;
  // }

  // @Bean
  // public RedisTemplate<?, ?> redisTemplate() {
  // RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
  // redisTemplate.setConnectionFactory(redisConnectionFactory());
  // return redisTemplate;
  // }
}
