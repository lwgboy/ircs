package com.github.hasoo.ircs.core.enums;

public enum MsgType {
  SMS("SMS"), MMS("MMS"), ALM("ALM"), FRD("FRD");

  private String type;

  MsgType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
