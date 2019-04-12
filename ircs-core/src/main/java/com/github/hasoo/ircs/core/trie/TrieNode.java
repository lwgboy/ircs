package com.github.hasoo.ircs.core.trie;

import java.util.HashMap;
import java.util.List;
import lombok.Data;

/**
 * @author hasoo
 */

@Data
public class TrieNode {
  private HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
  private String text = "";
  private boolean isWord = false;
  private boolean isSingle = false;

  private List<GroupPatternNode> groupList = null;

  public TrieNode() {}

  public TrieNode(String text) {
    this.text = text;
  }
}
