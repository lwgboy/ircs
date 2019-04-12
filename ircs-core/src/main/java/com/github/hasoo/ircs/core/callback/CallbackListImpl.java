package com.github.hasoo.ircs.core.callback;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import org.apache.commons.collections4.map.MultiKeyMap;

/**
 * @author hasoo
 */
public class CallbackListImpl implements CallbackList {
  private MultiKeyMap<String, Date> whiteCallbackLists = null;
  private HashMap<String, Character> ignores = null;

  public CallbackListImpl() {
    this.whiteCallbackLists = new MultiKeyMap<String, Date>();
    this.ignores = new HashMap<>();
  }

  @Override
  public void putWhiteCallback(String username, String callback, Date modDate) {
    whiteCallbackLists.put(username, callback, modDate);
  }

  @Override
  public Optional<Date> getWhiteCallback(String username, String callback) {
    return Optional.ofNullable(whiteCallbackLists.get(username, callback));
  }

  @Override
  public void putIgnore(String username, Character type) {
    ignores.put(username, type);
  }

  @Override
  public Optional<Character> getIgnore(String username) {
    return Optional.ofNullable(ignores.get(username));
  }
}
