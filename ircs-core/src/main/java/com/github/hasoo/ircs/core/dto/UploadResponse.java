package com.github.hasoo.ircs.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResponse {

  private String name;
  private String type;
  private String uri;
  private String code;
  private String desc;
}
