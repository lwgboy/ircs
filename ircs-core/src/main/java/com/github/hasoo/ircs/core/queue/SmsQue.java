package com.github.hasoo.ircs.core.queue;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsQue {
  private String msgKey;
  private String userKey;
  private String groupname;
  private String username;
  private String phone;
  private String callback;
  private String message;
  private LocalDateTime resDate;
}
