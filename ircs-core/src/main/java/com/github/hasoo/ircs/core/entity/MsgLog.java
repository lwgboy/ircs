package com.github.hasoo.ircs.core.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MsgLog {
  @Id
  private String msgKey;
  private String userKey;
  private String groupname;
  private String username;
  private Date resDate;
  private Date routeDate;
  private Date sentDate;
  private Date doneDate;
  private Date reportDate;
  private Integer status;
  private String msgType;
  private String contentType;
  private String phone;
  private String callback;
  private String message;
  private String resultCode;
  private String resultDesc;
  private String net;
  private String sender;
}
