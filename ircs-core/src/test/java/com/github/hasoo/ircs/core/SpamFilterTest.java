package com.github.hasoo.ircs.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.hasoo.ircs.core.spam.SpamFilter;
import com.github.hasoo.ircs.core.spam.map.SpamMapper;
import com.github.hasoo.ircs.core.trie.GroupPatternNode;
import com.github.hasoo.ircs.core.trie.Trie;
import com.github.hasoo.ircs.core.trie.TrieImpl;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
public class SpamFilterTest {
  @Value("${ircs.spamfilter.json.phone}")
  private String spamPhoneJson;
  @Value("${ircs.spamfilter.json.word}")
  private String spamWordJson;

  @Test
  public void testSpamMapper() {
    SpamMapper spamMapper = new SpamMapper(this.spamPhoneJson, this.spamWordJson);
    HashMap<String, List<String>> spamWords = spamMapper.getSpamWord();
    Assert.assertTrue(null != spamWords.get("test"));
    Assert.assertTrue(null != spamWords.get("aaa"));
    Assert.assertTrue(null != spamWords.get("bbb"));

    HashMap<String, List<String>> spamPhones = spamMapper.getSpamPhone();
    Assert.assertTrue(null != spamPhones.get("test"));
    Assert.assertTrue(null != spamPhones.get("test1"));
  }

  @Test
  public void testFindAll() {
    Trie trie = new TrieImpl();

    trie.add("d");
    trie.add("abc");

    List<String> result = trie.findAll("abd");
    Assert.assertEquals(Arrays.asList("d"), result);
  }

  @Test
  public void testGroup() {
    Trie trie = new TrieImpl();

    trie.add("바나나", 1, 2);
    trie.add("사과", 1, 2);
    trie.add("바나나", 2, 2);
    trie.add("토마토", 2, 2);
    trie.add("사과");

    Assert.assertTrue(trie.isIncludingPattern("바나나"));
    Assert.assertTrue(trie.isIncludingPattern("사과"));
    Assert.assertFalse(trie.isIncludingPattern("바나나나"));


    trie.getMatchedLeafNode("사과").ifPresent(node -> {
      Assert.assertEquals("사과", node.getText());
      Assert.assertTrue(node.isSingle());

      List<GroupPatternNode> groupPatternNodes = node.getGroupList();

      if (null != groupPatternNodes) {
        Iterator<GroupPatternNode> itor = groupPatternNodes.iterator();
        while (itor.hasNext()) {
          GroupPatternNode groupPatternNode = itor.next();
          Assert.assertEquals(1, groupPatternNode.getGroupKey().intValue());
          Assert.assertEquals(2, groupPatternNode.getGroupMemberCount().intValue());
        }
      }
    });

    Assert.assertEquals(Arrays.asList("사과"), trie.find("원숭이는 사과와 바나나를 좋아한다."));
    Assert.assertEquals(Arrays.asList("토마토", "바나나"), trie.find("원숭이는 바나나와 토마토를 좋아한다."));
  }

  @Test
  public void testDuplicatePattern() {
    Trie trie = new TrieImpl();

    trie.add("a");
    trie.add("a");
    trie.add("d");
    trie.add("d");
    trie.add("ab");
    trie.add("ab");
    trie.add("abc");
    trie.add("abc");

    Assert.assertEquals(trie.getPatternsCount(), 4);

    Assert.assertTrue(trie.isIncludingPattern("a"));
    Assert.assertTrue(trie.isIncludingPattern("ab"));
    Assert.assertTrue(trie.isIncludingPattern("abc"));
    Assert.assertTrue(trie.isIncludingPattern("d"));

    Assert.assertEquals(Arrays.asList("a", "a", "ab", "a", "d", "d", "a", "ab", "abc"),
        trie.findAll("This is a, ab add c, is abc"));

    trie.add("i");
    Assert.assertEquals(5, trie.getPatternsCount());
    Assert.assertEquals(
        Arrays.asList("i", "i", "a", "a", "ab", "a", "d", "d", "i", "a", "ab", "abc"),
        trie.findAll("This is a, ab add c, is abc"));
  }

  @Test
  public void testSpamPhone() {
    SpamFilter spam = new SpamFilter(new SpamMapper(this.spamPhoneJson, this.spamWordJson));
    spam.setupSpamPhone();

    Assert.assertFalse(spam.isSpamPhone("test4", "01099999999"));
    Assert.assertTrue(spam.isSpamPhone("test4", "01077777777"));
    Assert.assertTrue(spam.isSpamPhone("test1", "01044444444"));
  }

  @Test
  public void testSpamWord() {
    SpamFilter spam = new SpamFilter(new SpamMapper(this.spamPhoneJson, this.spamWordJson));
    spam.setupSpamWord();

    Assert.assertTrue(0 == spam.isSpamWord("test4", "The Some").size());
    Assert.assertTrue(1 == spam.isSpamWord("test4", "The some").size());
    Assert.assertTrue(2 == spam.isSpamWord("test4", "it's cool").size());
    Assert.assertTrue(0 < spam.isSpamWord("test", "it's abc").size());
  }
}
