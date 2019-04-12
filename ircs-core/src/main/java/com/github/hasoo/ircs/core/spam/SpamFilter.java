package com.github.hasoo.ircs.core.spam;

import com.github.hasoo.ircs.core.spam.map.SpamMapper;
import com.github.hasoo.ircs.core.trie.Trie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.github.hasoo.ircs.core.trie.TrieImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpamFilter {
  private SpamMapper spamMapper;

  Trie defaultSpamWordTrie;
  Trie defaultSpamPhoneTrie;

  private HashMap<String, Trie> spamWordTrie;
  private HashMap<String, Trie> spamPhoneTrie;

  @SuppressWarnings("unused")
  private SpamFilter() {}

  public SpamFilter(SpamMapper mapper) {
    this.spamMapper = mapper;
  }

  public synchronized List<String> isSpamWord(String username, String message) {
    Trie trie = Optional.ofNullable(spamWordTrie.get(username)).orElse(this.defaultSpamWordTrie);
    return trie.find(message);
  }

  public synchronized boolean isSpamPhone(String username, String phone) {
    Trie trie = Optional.ofNullable(spamPhoneTrie.get(username)).orElse(this.defaultSpamPhoneTrie);
    return trie.isIncludingPattern(phone);
  }

  public synchronized void setupSpamWord() {
    spamWordTrie = new HashMap<>();
    Trie trie = null;

    HashMap<String, List<String>> spamWordMap = spamMapper.getSpamWord();
    for (Map.Entry<String, List<String>> em : spamWordMap.entrySet()) {
      log.debug("spam:{} word:{}", em.getKey(), em.getValue());
      trie = new TrieImpl();
      if ("ALL" == em.getKey()) {
        this.defaultSpamWordTrie = trie;
      } else {
        spamWordTrie.put(em.getKey(), trie);
      }

      int groupKey = 0;
      List<String> wordLists = em.getValue();
      for (String word : wordLists) {
        String[] arrayWord = word.split("&");
        if (1 == arrayWord.length) {
          trie.add(word);
        } else {
          groupKey++;
          for (int i = 0; i < arrayWord.length; i++) {
            trie.add(arrayWord[i], groupKey, arrayWord.length);
          }
        }
      }
    }
  }

  public synchronized void setupSpamPhone() {
    spamPhoneTrie = new HashMap<>();
    Trie trie = null;

    HashMap<String, List<String>> spamPhoneMap = spamMapper.getSpamPhone();
    for (Map.Entry<String, List<String>> em : spamPhoneMap.entrySet()) {
      log.debug("spam:{} phone:{}", em.getKey(), em.getValue());
      trie = new TrieImpl();
      if ("ALL" == em.getKey()) {
        this.defaultSpamPhoneTrie = trie;
      } else {
        spamPhoneTrie.put(em.getKey(), trie);
      }

      List<String> phoneLists = em.getValue();
      for (String phone : phoneLists) {
        trie.add(phone);
      }
    }
  }
}
