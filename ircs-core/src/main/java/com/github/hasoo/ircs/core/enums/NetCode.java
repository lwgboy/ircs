package com.github.hasoo.ircs.core.enums;

public enum NetCode {
  SKT("SKT"), KTF("KTF"), LGT("LGT"), KAK("KAK"), ETC("ETC");

  private String code;

  NetCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
