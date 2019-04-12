package com.github.hasoo.ircs.core.callback;

import com.github.hasoo.ircs.core.callback.map.CallbackMap;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.hasoo.ircs.core.callback.map.CallbackMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallbackFilter extends AbstractWhiteCallbackListLoader {
  private CallbackList callbackList = null;
  private CallbackMapper callbackMapper = null;
  private CallbackRegulations callbackRegulations = new CallbackRegulations();

  @SuppressWarnings("unused")
  private CallbackFilter() {}

  public CallbackFilter(CallbackMapper mapper) {
    this.callbackMapper = mapper;
  }

  public synchronized void setupWhiteCallbackList() {
    this.callbackList = load();
  }

  public synchronized boolean isWhiteCallback(String username, String callback) {
    return this.callbackList.getWhiteCallback(username, callback).isPresent();
  }

  public synchronized boolean isComply(String callback, boolean blockSpecialCallback) {
    return this.callbackRegulations.isComply(callback, blockSpecialCallback);
  }

  public synchronized Character isIgnore(String username) {
    return this.callbackList.getIgnore(username).orElse('n');
  }

  @Override
  public CallbackList loadByStorage(CallbackList refreshCallbackList) {
    CallbackMap callbackMap = this.callbackMapper.getCallback();

    HashMap<String, List<String>> whiteCallbacks = callbackMap.getWhiteCallbacks();
    for (Map.Entry<String, List<String>> em : whiteCallbacks.entrySet()) {
      List<String> whiteCallbackLists = em.getValue();
      for (String whiteCallback : whiteCallbackLists) {
        log.debug("username:{} callback:{}", em.getKey(), whiteCallback);
        refreshCallbackList.putWhiteCallback(em.getKey(), whiteCallback, new Date());
      }
    }

    HashMap<String, Character> ignores = callbackMap.getIgnores();
    for (Map.Entry<String, Character> em : ignores.entrySet()) {
      log.debug("username:{} ignore:{}", em.getKey(), em.getValue());
      refreshCallbackList.putIgnore(em.getKey(), em.getValue());
    }

    return refreshCallbackList;
  }
}
