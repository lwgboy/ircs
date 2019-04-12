package com.github.hasoo.ircs.core.callback.map;

import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallbackMap {
  private HashMap<String, List<String>> whiteCallbacks;
  private HashMap<String, Character> ignores;
}
