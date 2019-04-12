package com.github.hasoo.ircs.core.callback;

import java.util.Date;
import java.util.Optional;

/**
 * @author hasoo
 */
public interface CallbackList {
  public void putWhiteCallback(String username, String callback, Date modDate);

  public Optional<Date> getWhiteCallback(String username, String callback);

  public void putIgnore(String username, Character type);

  public Optional<Character> getIgnore(String username);
}
