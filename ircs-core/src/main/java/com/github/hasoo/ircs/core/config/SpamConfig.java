package com.github.hasoo.ircs.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.hasoo.ircs.core.spam.JpaSpamJsonWriter;
import com.github.hasoo.ircs.core.spam.SpamFilter;
import com.github.hasoo.ircs.core.spam.SpamJsonWriter;
import com.github.hasoo.ircs.core.spam.map.SpamMapper;

@Configuration
public class SpamConfig {
  @Value("${ircs.spamfilter.json.phone}")
  private String spamPhoneJson;
  @Value("${ircs.spamfilter.json.word}")
  private String spamWordJson;

  @Bean
  public SpamJsonWriter jpaSpamJsonWriter() {
    return new JpaSpamJsonWriter();
  }

  @Bean
  public SpamMapper spamMapper() {
    return new SpamMapper(spamPhoneJson, spamWordJson);
  }

  @Bean
  public SpamFilter spamFilter() {
    SpamFilter spamFilter = new SpamFilter(spamMapper());
    spamFilter.setupSpamPhone();
    spamFilter.setupSpamWord();
    return spamFilter;
  }
}
