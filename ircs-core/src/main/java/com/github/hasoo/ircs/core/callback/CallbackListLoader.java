package com.github.hasoo.ircs.core.callback;

/**
 * To set up White Callback List, Implement a class extend AbstractWhiteCallbackListLoader
 * 
 * @see AbstractWhiteCallbackListLoader
 * @author hasoo
 */
public interface CallbackListLoader {

  public CallbackList loadByStorage(CallbackList refreshCallbackList);
}
