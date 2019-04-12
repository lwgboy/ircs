package com.github.hasoo.ircs.core.router.map;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoutingMapper {
  private ObjectMapper mapper = new ObjectMapper();

  private String clientRoutingJson;
  private String routingJson;

  @SuppressWarnings("unused")
  private RoutingMapper() {}

  public RoutingMapper(String clientRoutingJson, String routingJson) {
    this.clientRoutingJson = clientRoutingJson;
    this.routingJson = routingJson;
  }

  public Optional<ClientRoutingMap> loadClientRouting() {
    ClientRoutingMap clientRoutingMap = null;

    try {
      // URL urlJsonClientRouting = Optional
      // .ofNullable(getClass().getClassLoader().getResource(this.clientRoutingJson))
      // .orElseThrow(() -> new FileNotFoundException(this.clientRoutingJson + " doesn't exist"));
      Path pathJsonClientRouting = Paths.get(this.clientRoutingJson);
      if (true != pathJsonClientRouting.toFile().exists()) {
        new FileNotFoundException(this.clientRoutingJson + " doesn't exist");
      }
      clientRoutingMap =
          mapper.readValue(pathJsonClientRouting.toUri().toURL(), ClientRoutingMap.class);
      log.debug("client routing:{}", clientRoutingMap.toString());
    } catch (IOException e) {
      log.error(e.getMessage());
    }

    return Optional.ofNullable(clientRoutingMap);
  }

  public Optional<RoutingMap> loadRouting() {
    RoutingMap routingMap = null;

    try {
      // URL urlJsonRouting =
      // Optional.ofNullable(getClass().getClassLoader().getResource(this.routingJson))
      // .orElseThrow(() -> new FileNotFoundException(this.routingJson + " doesn't exist"));
      Path pathJsonRouting = Paths.get(this.routingJson);
      if (true != pathJsonRouting.toFile().exists()) {
        new FileNotFoundException(this.routingJson + " doesn't exist");
      }
      routingMap = mapper.readValue(pathJsonRouting.toUri().toURL(), RoutingMap.class);
      log.debug("routing:{}", routingMap.toString());
    } catch (IOException e) {
      log.error(e.getMessage());
    }

    return Optional.ofNullable(routingMap);
  }
}
