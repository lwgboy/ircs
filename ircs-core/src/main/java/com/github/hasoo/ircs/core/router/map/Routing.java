package com.github.hasoo.ircs.core.router.map;

import java.util.HashMap;
import java.util.List;
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
public class Routing {
  private String type;
  private HashMap<String, List<Sender>> senders;

  public Optional<List<Sender>> getSender(String messageType) {
    return Optional.ofNullable(senders.get(messageType));
  }
}
