package com.github.hasoo.ircs.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IrcsApplication {

  public static void main(String[] args) {
    SpringApplication.run(IrcsApplication.class, args);
  }

  // @Bean
  // CommandLineRunner runner() {
  // return (a) -> {
  // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  // log.info(passwordEncoder.encode("test"));
  // };
  // }

  // @Autowired
  // private UserRedisRepository userRedisRepository;
  //
  // @Bean
  // CommandLineRunner runner() {
  // return (a) -> {
  // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  //
  // User user = new User("test", passwordEncoder.encode("test"), new String[] {"ROLE_USER"}, true,
  // true, true, true);
  // userRedisRepository.save(user);
  // };
  // }
}
