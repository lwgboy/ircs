package com.github.hasoo.ircs.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageResponse {
  private String userKey;
  private String desc;
  private String code;
}
