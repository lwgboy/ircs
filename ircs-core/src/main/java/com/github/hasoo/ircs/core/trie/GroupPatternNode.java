package com.github.hasoo.ircs.core.trie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hasoo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupPatternNode {
  private Integer groupKey = null;
  private Integer groupMemberCount = 0;
}
