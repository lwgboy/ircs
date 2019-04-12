package com.github.hasoo.ircs.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.hasoo.ircs.core.callback.CallbackFilter;
import com.github.hasoo.ircs.core.callback.map.CallbackMap;
import com.github.hasoo.ircs.core.callback.map.CallbackMapper;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
public class CallbackFilterTest {
  @Value("${ircs.callbackfilter.json.callback}")
  private String callbackJson;

  private CallbackMapper callbackMapper = null;
  private CallbackFilter callbackFilter = null;

  @Before
  public void setUp() {
    this.callbackMapper = new CallbackMapper(callbackJson);
    this.callbackFilter = new CallbackFilter(this.callbackMapper);
    this.callbackFilter.setupWhiteCallbackList();
  }

  @Test
  public void testCallbackMapper() {
    CallbackMap callbackMap = this.callbackMapper.getCallback();
    Assert.assertTrue(callbackMap.getWhiteCallbacks().containsKey("test"));
    Assert.assertTrue(callbackMap.getWhiteCallbacks().containsKey("test1"));
    Assert.assertTrue(callbackMap.getIgnores().containsKey("test2"));
    Assert.assertTrue(callbackMap.getIgnores().containsKey("test3"));
  }

  @Test
  public void testWhiteCallback() {
    Assert.assertTrue(this.callbackFilter.isWhiteCallback("test", "01011111111"));
    Assert.assertFalse(this.callbackFilter.isWhiteCallback("test", "01033333333"));
    Assert.assertFalse(this.callbackFilter.isWhiteCallback("test3", "01033333333"));
  }

  @Test
  public void testIgnore() {
    Assert.assertTrue('n' == this.callbackFilter.isIgnore("test"));
    Assert.assertTrue('h' == this.callbackFilter.isIgnore("test2"));
    Assert.assertTrue('y' == this.callbackFilter.isIgnore("test3"));
  }

  @Test
  public void testComply() {
    Assert.assertFalse(this.callbackFilter.isComply("02123", true));
    Assert.assertTrue(this.callbackFilter.isComply("01021111111", true));
    Assert.assertFalse(this.callbackFilter.isComply("01011111111", true));
    Assert.assertTrue(this.callbackFilter.isComply("#123", true));
    Assert.assertTrue(this.callbackFilter.isComply("#123", false));
    Assert.assertTrue(this.callbackFilter.isComply("100", false));
    Assert.assertFalse(this.callbackFilter.isComply("189", false));
    Assert.assertTrue(this.callbackFilter.isComply("1382", false));
    Assert.assertFalse(this.callbackFilter.isComply("1400", false));
    Assert.assertTrue(this.callbackFilter.isComply("02120", false));
    Assert.assertFalse(this.callbackFilter.isComply("02121", false));
    Assert.assertTrue(this.callbackFilter.isComply("043120", false));
    Assert.assertFalse(this.callbackFilter.isComply("043121", false));
    Assert.assertTrue(this.callbackFilter.isComply("15881111", false));
    Assert.assertFalse(this.callbackFilter.isComply("17881111", false));
    Assert.assertTrue(this.callbackFilter.isComply("029999999", false));
    Assert.assertFalse(this.callbackFilter.isComply("021111111", false));
    Assert.assertFalse(this.callbackFilter.isComply("020111111", false));
    Assert.assertTrue(this.callbackFilter.isComply("0299999999", false));
    Assert.assertFalse(this.callbackFilter.isComply("0211111111", false));
    Assert.assertFalse(this.callbackFilter.isComply("0201111111", false));
    Assert.assertTrue(this.callbackFilter.isComply("0312222222", false));
    Assert.assertFalse(this.callbackFilter.isComply("0310111111", false));
    Assert.assertFalse(this.callbackFilter.isComply("0311111111", false));
    Assert.assertTrue(this.callbackFilter.isComply("06422222222", false));
    Assert.assertFalse(this.callbackFilter.isComply("06401111111", false));
    Assert.assertFalse(this.callbackFilter.isComply("06411111111", false));
    Assert.assertTrue(this.callbackFilter.isComply("01022222222", false));
    Assert.assertFalse(this.callbackFilter.isComply("0102222222", false));
    Assert.assertFalse(this.callbackFilter.isComply("01011111111", false));
    Assert.assertTrue(this.callbackFilter.isComply("0111111111", false));
    Assert.assertTrue(this.callbackFilter.isComply("01111111111", false));
    Assert.assertFalse(this.callbackFilter.isComply("0110111111", false));
    Assert.assertTrue(this.callbackFilter.isComply("0121111111", false));
    Assert.assertTrue(this.callbackFilter.isComply("04011111111", false));
    Assert.assertTrue(this.callbackFilter.isComply("060123456789", false));
    Assert.assertFalse(this.callbackFilter.isComply("010123456789", false));
    Assert.assertFalse(this.callbackFilter.isComply("0601234567890", false));
    Assert.assertTrue(this.callbackFilter.isComply("00912345678901", false));
    Assert.assertFalse(this.callbackFilter.isComply("009123456789012", false));
    Assert.assertFalse(this.callbackFilter.isComply("0091234567890", false));
  }
}
