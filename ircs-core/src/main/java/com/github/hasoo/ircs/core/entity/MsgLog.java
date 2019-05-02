package com.github.hasoo.ircs.core.entity;

import java.time.LocalDateTime;
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
  private String groupKey;
  private String userKey;
  private String groupname;
  private String username;
  private LocalDateTime resDate;
  private LocalDateTime routeDate;
  private LocalDateTime sentDate;
  private LocalDateTime doneDate;
  private LocalDateTime reportDate;
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
