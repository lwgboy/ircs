package com.github.hasoo.ircs.core.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequest {
  @NotEmpty
  private String key;
  @NotEmpty
  private String phone;
  @NotEmpty
  private String callback;
  @NotEmpty
  private String message;
}
