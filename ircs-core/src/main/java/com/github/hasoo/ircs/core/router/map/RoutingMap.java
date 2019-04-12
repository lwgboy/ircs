package com.github.hasoo.ircs.core.router.map;

import java.util.HashMap;
import java.util.Optional;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoutingMap {
  private HashMap<String, Routing> routings;

  public Optional<Routing> getRouting(String routingName) {
    return Optional.ofNullable(routings.get(routingName));
  }
}
