package com.github.hasoo.ircs.core.queue;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageQue {
  private String msgKey;
  private String userKey;
  private String groupname;
  private String username;
  private Double fee;
  private Date resDate;
  private String msgType;
  private String contentType;
  private String phone;
  private String callback;
  private String message;
}
