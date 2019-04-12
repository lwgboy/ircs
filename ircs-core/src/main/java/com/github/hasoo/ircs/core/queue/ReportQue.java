package com.github.hasoo.ircs.core.queue;

import java.util.Date;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReportQue {
  private String username;
  private String msgKey;
  private String userKey;
  private String phone;
  private String code;
  private String desc;
  private Date doneDate;
  private String net;
}
