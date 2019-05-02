package com.github.hasoo.ircs.core.enums;

public enum ContentPriceCode {
  SMS("SMS"), TXT("TXT"), IMG("IMG"), IMT("IMG");

  private String code;

  ContentPriceCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
