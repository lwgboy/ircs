package com.github.hasoo.ircs.core.config;

import com.github.hasoo.ircs.core.router.JpaRoutingJsonWriter;
import com.github.hasoo.ircs.core.router.Router;
import com.github.hasoo.ircs.core.router.RoutingJsonWriter;
import com.github.hasoo.ircs.core.router.map.RoutingMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {
  @Value("${ircs.router.json.client_routing}")
  private String clientRoutingJson;
  @Value("${ircs.router.json.routing}")
  private String routingJson;

  @Bean
  public RoutingJsonWriter jpaRoutingJsonWriter() {
    return new JpaRoutingJsonWriter();
  }

  @Bean
  public RoutingMapper routingMapper() {
    return new RoutingMapper(clientRoutingJson, routingJson);
  }

  @Bean
  public Router router() {
    Router router = new Router(routingMapper());
    router.setupClientRouting();
    router.setupRouting();
    return router;
  }
}
