package com.github.hasoo.ircs.core.spam.map;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpamMapper {
  private ObjectMapper mapper = new ObjectMapper();

  private String spamPhoneJson;
  private String spamWordJson;

  @SuppressWarnings("unused")
  private SpamMapper() {}

  public SpamMapper(String spamPhoneJson, String spamWordJson) {
    this.spamPhoneJson = spamPhoneJson;
    this.spamWordJson = spamWordJson;
  }

  public HashMap<String, List<String>> getSpamWord() {
    SpamWordMap spamWordMap =
        loadSpamWord().orElseThrow(() -> new RuntimeException("failed to load SpamWordMap"));
    return spamWordMap.getSpamWords();
  }

  public HashMap<String, List<String>> getSpamPhone() {
    SpamPhoneMap spamPhoneMap =
        loadSpamPhone().orElseThrow(() -> new RuntimeException("failed to load SpamPhonerMap"));
    return spamPhoneMap.getSpamPhones();
  }

  private Optional<SpamPhoneMap> loadSpamPhone() {
    SpamPhoneMap spamPhoneMap = null;
    try {
      // URL urlJsonSpamPhone =
      // Optional.ofNullable(getClass().getClassLoader().getResource(this.spamPhoneJson))
      // .orElseThrow(() -> new FileNotFoundException(this.spamPhoneJson + " doesn't exist"));
      Path pathJsonSpamPhone = Paths.get(this.spamPhoneJson);
      if (true != pathJsonSpamPhone.toFile().exists()) {
        new FileNotFoundException(this.spamPhoneJson + " doesn't exist");
      }
      spamPhoneMap = mapper.readValue(pathJsonSpamPhone.toUri().toURL(), SpamPhoneMap.class);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return Optional.ofNullable(spamPhoneMap);
  }

  private Optional<SpamWordMap> loadSpamWord() {
    SpamWordMap spamWordMap = null;
    try {
      // URL urlJsonSpamWord =
      // Optional.ofNullable(getClass().getClassLoader().getResource(this.spamWordJson))
      // .orElseThrow(() -> new FileNotFoundException(this.spamWordJson + " doesn't exist"));
      Path pathJsonSpamWord = Paths.get(this.spamWordJson);
      if (true != pathJsonSpamWord.toFile().exists()) {
        new FileNotFoundException(this.spamPhoneJson + " doesn't exist");
      }
      spamWordMap = mapper.readValue(pathJsonSpamWord.toUri().toURL(), SpamWordMap.class);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return Optional.ofNullable(spamWordMap);
  }

}
