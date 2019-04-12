package com.github.hasoo.ircs.core.config;

import com.github.hasoo.ircs.core.callback.CallbackFilter;
import com.github.hasoo.ircs.core.callback.CallbackJsonWriter;
import com.github.hasoo.ircs.core.callback.JpaCallbackJsonWriter;
import com.github.hasoo.ircs.core.callback.map.CallbackMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallbackConfig {
  @Value("${ircs.callbackfilter.json.callback}")
  private String callbackJson;

  @Bean
  public CallbackJsonWriter jpaCallbackJsonWriter() {
    return new JpaCallbackJsonWriter();
  }

  @Bean
  public CallbackMapper callbackMapper() {
    return new CallbackMapper(callbackJson);
  }

  @Bean
  public CallbackFilter callbackFilter() {
    CallbackFilter callbackFilter = new CallbackFilter(callbackMapper());
    callbackFilter.setupWhiteCallbackList();
    return callbackFilter;
  }
}
