package com.github.hasoo.ircs.core.dto;

import java.util.HashMap;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

  @NotEmpty
  private String key;
  @NotEmpty
  private String phone;
  private HashMap<String, String> templates;
}
