package com.github.hasoo.ircs.core.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingQue {
  private String username;
  private double fee;
}
