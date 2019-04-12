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
public class ClientRoutingMap {
  private HashMap<String, String> clientRoutings;

  public Optional<String> getRoutingName(String username) {
    return Optional.ofNullable(clientRoutings.get(username));
  }
}
