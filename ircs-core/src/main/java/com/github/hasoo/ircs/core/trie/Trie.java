package com.github.hasoo.ircs.core.trie;

import java.util.List;
import java.util.Optional;

/**
 * @author hasoo
 */

public interface Trie {

  public int getPatternsCount();

  public boolean add(String word);

  public boolean add(String word, Integer groupKey, int groupMemberCount);

  public Optional<TrieNode> getMatchedLeafNode(String pattern);

  public boolean isIncludingPattern(String pattern);

  public List<String> findAll(String str);

  public List<String> find(String str);
}
