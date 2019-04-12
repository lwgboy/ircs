package com.github.hasoo.ircs.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.hasoo.ircs.core.router.map.ClientRoutingMap;
import com.github.hasoo.ircs.core.router.map.RoutingMap;
import com.github.hasoo.ircs.core.router.map.RoutingMapper;

@RunWith(SpringRunner.class)
// @ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
// @TestPropertySource(
// properties = {"ircs.router.json.client_routing=client_routing.json",
// "ircs.router.json.routing=routing.json"})
@TestPropertySource("classpath:test.properties")
public class RouterTest {
  @Value("${ircs.router.json.client_routing}")
  private String clientRoutingJson;
  @Value("${ircs.router.json.routing}")
  private String routingJson;

  // @BeforeClass
  // public static void setUpProperties() {
  // System.setProperty("ircs.router.json.client_routing", "client_routing.json");
  // System.setProperty("ircs.router.json.routing", "routing.json");
  // }

  @Test
  public void testRouterMapper() {
    RoutingMapper mapper = new RoutingMapper(clientRoutingJson, routingJson);

    ClientRoutingMap clientRoutingMap = mapper.loadClientRouting()
        .orElseThrow(() -> new RuntimeException("failed to load ClientRoutingMap"));

    Assert.assertTrue(clientRoutingMap.getClientRoutings().containsKey("test1"));

    RoutingMap routingMap =
        mapper.loadRouting().orElseThrow(() -> new RuntimeException("failed to laod loadRouting"));

    Assert.assertTrue(routingMap.getRoutings().containsKey("adv"));
  }
}
