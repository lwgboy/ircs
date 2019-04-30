package com.github.hasoo.ircs.core.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipleSms {

  @NotEmpty
  private String groupKey;
  @NotEmpty
  private List<Phone> phones;
  @NotEmpty
  private String callback;
  @NotEmpty
  private String message;
}
