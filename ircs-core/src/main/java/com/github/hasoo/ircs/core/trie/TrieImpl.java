package com.github.hasoo.ircs.core.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author hasoo
 */

public class TrieImpl implements Trie {

  private TrieNode root = new TrieNode();
  private int patternsCount = 0;

  public int getPatternsCount() {
    return this.patternsCount;
  }

  public boolean add(String word) {
    return add(word, null, 0);
  }

  public boolean add(String word, Integer groupKey, int groupMemberCount) {
    TrieNode trie = root;
    if (trie == null || word == null) {
      return false;
    }

    char[] chars = word.toCharArray();
    int counter = 0;
    while (counter < chars.length) {
      Set<Character> childs = trie.getChildren().keySet();
      if (!childs.contains(chars[counter])) {
        insertChar(trie, chars[counter]);
        if (counter == chars.length - 1) {
          TrieNode node = getChild(trie, chars[counter]);
          node.setWord(true);
          setGroupOrSinglePattern(node, groupKey, groupMemberCount);
          this.patternsCount++;

          return true;
        }
      }

      trie = getChild(trie, chars[counter]);
      if (trie.getText().equals(word)) {
        if (true != trie.isWord()) {
          trie.setWord(true);
          this.patternsCount++;
        }
        setGroupOrSinglePattern(trie, groupKey, groupMemberCount);

        return true;
      }
      counter++;
    }

    return false;
  }

  private void setGroupOrSinglePattern(TrieNode node, Integer groupKey, int groupMemberCount) {
    if (null != groupKey) {
      List<GroupPatternNode> groupLists = node.getGroupList();
      if (null == groupLists) {
        node.setGroupList(new ArrayList<GroupPatternNode>(
            Arrays.asList(new GroupPatternNode(groupKey, groupMemberCount))));
      } else {
        groupLists.add(new GroupPatternNode(groupKey, groupMemberCount));
      }
    } else {
      node.setSingle(true);
    }
  }

  public Optional<TrieNode> getMatchedLeafNode(String pattern) {

    Map<Character, TrieNode> children = root.getChildren();
    TrieNode node = null;
    TrieNode leafNode = null;
    for (int i = 0; i < pattern.length(); i++) {
      char c = pattern.charAt(i);
      if (children.containsKey(c)) {
        node = children.get(c);
        children = node.getChildren();
        if (node.isWord()) {
          leafNode = node;
        }
      } else {
        leafNode = null;
        break;
      }
    }
    return Optional.ofNullable(leafNode);
  }

  public boolean isIncludingPattern(String pattern) {
    boolean isExist = false;
    Map<Character, TrieNode> children = root.getChildren();
    TrieNode node = null;
    for (int i = 0; i < pattern.length(); i++) {
      char c = pattern.charAt(i);
      if (children.containsKey(c)) {
        node = children.get(c);
        children = node.getChildren();
        if (node.isWord()) {
          isExist = true;
        }
      } else {
        isExist = false;
        break;
      }
    }
    return isExist;
  }

  public List<String> findAll(String str) {
    // I don't like the recursive method. because it's difficult to read the code and manage memory.
    List<String> matchedList = new ArrayList<String>();
    Map<Character, TrieNode> children = root.getChildren();
    TrieNode node = null;

    int foundIndex = 0;
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (children.containsKey(c)) {
        foundIndex++;
        node = children.get(c);
        children = node.getChildren();
        if (node.isWord()) {
          matchedList.add(node.getText());
        }
      } else {
        children = root.getChildren();
        i = i - foundIndex;
        foundIndex = 0;
      }
    }

    return matchedList;
  }

  public List<String> find(String str) {
    HashSet<String> patternSet = null;
    TrieNode node = null;
    HashMap<Integer, HashSet<String>> searchedGroupPatterns = new HashMap<>();
    Map<Character, TrieNode> children = root.getChildren();

    int foundIndex = 0;
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (children.containsKey(c)) {
        foundIndex++;
        node = children.get(c);
        children = node.getChildren();
        if (node.isWord()) {
          if (node.isSingle()) {
            return new ArrayList<String>(Arrays.asList(node.getText()));
          }

          List<GroupPatternNode> groupLists = node.getGroupList();
          if (null != groupLists) {
            Iterator<GroupPatternNode> itor = groupLists.iterator();
            while (itor.hasNext()) {
              GroupPatternNode groupPatternNode = itor.next();
              Integer groupKey = groupPatternNode.getGroupKey();
              patternSet = searchedGroupPatterns.get(groupKey);
              if (null == patternSet) {
                searchedGroupPatterns.put(groupKey,
                    new HashSet<String>(Arrays.asList(node.getText())));
              } else {
                patternSet.add(node.getText());
                if (groupPatternNode.getGroupMemberCount() == patternSet.size()) {
                  return new ArrayList<String>(patternSet);
                }
              }
            }
          }
        }
      } else {
        children = root.getChildren();
        i = i - foundIndex;
        foundIndex = 0;
      }
    }

    return new ArrayList<String>();
  }

  private TrieNode getChild(TrieNode trie, Character c) {
    return trie.getChildren().get(c);
  }

  private void insertChar(TrieNode trie, Character c) {
    if (trie.getChildren().containsKey(c)) {
      return;
    }

    TrieNode next = new TrieNode(trie.getText() + c.toString());
    trie.getChildren().put(c, next);
  }

  // public boolean removePattern(String pattern) {
  // return removeIfPattern(root, pattern, 0);
  // }
  //
  // private boolean removeIfPattern(TrieNode currentTrieNode, String pattern, int index) {
  // if (pattern.length() == index) {
  // if (!currentTrieNode.isWord()) {
  // return false;
  // }
  // currentTrieNode.setWord(false);
  // return currentTrieNode.getChildren().isEmpty();
  // }
  //
  // char c = pattern.charAt(index);
  // TrieNode childNode = currentTrieNode.getChildren().get(c);
  // if (null == childNode) {
  // return false;
  // }
  //
  // if (removeIfPattern(childNode, pattern, index + 1) && !childNode.isWord()) {
  // currentTrieNode.getChildren().remove(c);
  // return currentTrieNode.getChildren().isEmpty();
  // }
  //
  // return false;
  // }
}
