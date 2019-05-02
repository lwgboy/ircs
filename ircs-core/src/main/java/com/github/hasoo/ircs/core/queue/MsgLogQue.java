package com.github.hasoo.ircs.core.queue;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MsgLogQue {
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
