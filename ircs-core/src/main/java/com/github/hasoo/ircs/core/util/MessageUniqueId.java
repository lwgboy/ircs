package com.github.hasoo.ircs.core.util;

import java.util.UUID;

public class MessageUniqueId {
  public static String get() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
