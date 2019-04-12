package com.github.hasoo.ircs.core.callback;

/**
 * @author hasoo
 */
public abstract class AbstractWhiteCallbackListLoader implements CallbackListLoader {

  /**
   * Create WhiteCallbackList
   *
   * @return {@link com.iheart.CallbackList.ircs.callback.WhiteCallbackList}
   */
  public CallbackList load() {
    return loadByStorage(new CallbackListImpl());
  }

  /**
   * Get White Callback on various storages.
   * <p>
   * Put White Callback using
   * {@link CallbackList #putWhiteCallback(String, String, Date)}
   */
  public abstract CallbackList loadByStorage(CallbackList refreshCallbackList);
}
