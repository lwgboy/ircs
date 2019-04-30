package com.github.hasoo.ircs.core.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.Test;

public class MultipleSmsTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testConvert2Json() {
    MultipleSms multipleSms = new MultipleSms();
    multipleSms.setGroupKey("1");

    Phone phone1 = new Phone();
    phone1.setKey("11");
    phone1.setPhone("01011111111");
    phone1.setTemplates(new HashMap<String, String>() {{
      put("name", "hasoo");
      put("point", "123");
    }});

    Phone phone2 = new Phone();
    phone2.setKey("22");
    phone2.setPhone("01022222222");
    phone2.setTemplates(new HashMap<String, String>() {{
      put("name", "kim");
      put("point", "3");
    }});

    multipleSms.setPhones(Arrays.asList(phone1, phone2));
    multipleSms.setCallback("1004");
    multipleSms.setMessage("test message");

    try {
      System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(multipleSms));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}