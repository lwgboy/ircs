package com.github.hasoo.ircs.core.enums;

public enum ResultCode {
  SUCCESS("1000", "success"),
  INVALIDCALLBACK("3000", "invalid callback"),
  DENYCALLBACK("3010", "deny callback"),
  SPAMPHONE("3200", "spam phone"),
  SPAMMESSAGE("3210", "spam message");

  private final String code;
  private final String desc;

  ResultCode(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }
}
