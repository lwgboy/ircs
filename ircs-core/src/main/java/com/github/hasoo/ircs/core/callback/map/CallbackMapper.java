package com.github.hasoo.ircs.core.callback.map;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallbackMapper {
  private ObjectMapper mapper = new ObjectMapper();

  private String callbackJson;

  @SuppressWarnings("unused")
  private CallbackMapper() {}

  public CallbackMapper(String callbackJson) {
    this.callbackJson = callbackJson;
  }

  public CallbackMap getCallback() {
    return loadCallback().orElseThrow(() -> new RuntimeException("failed to load CallbackMap"));
  }

  private Optional<CallbackMap> loadCallback() {
    CallbackMap map = null;
    try {
      // URL urlJsonWhiteCallback =
      // Optional.ofNullable(getClass().getClassLoader().getResource(this.callbackJson))
      // .orElseThrow(() -> new FileNotFoundException(this.callbackJson + " doesn't exist"));
      Path pathJsonWhiteCallback = Paths.get(this.callbackJson);
      if (true != pathJsonWhiteCallback.toFile().exists()) {
        new FileNotFoundException(this.callbackJson + " doesn't exist");
      }
      map = mapper.readValue(pathJsonWhiteCallback.toUri().toURL(), CallbackMap.class);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return Optional.ofNullable(map);
  }

}
