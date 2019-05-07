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
public class MessageQue {

  private String msgKey;
  private String groupKey;
  private String userKey;
  private String groupname;
  private String username;
  private Double fee;
  private LocalDateTime resDate;
  private String msgType;
  private String contentType;
  private String phone;
  private String callback;
  private String message;
}
