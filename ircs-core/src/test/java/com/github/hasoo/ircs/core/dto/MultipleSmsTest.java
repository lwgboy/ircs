package com.github.hasoo.ircs.core.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.hasoo.ircs.core.queue.MessageQue;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

  @Test
  public void testMessageQue2Json() {
    try {
//      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//      mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//
//      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//      mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
//
//      mapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, true);
//      mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);

      SimpleModule module = new SimpleModule();
      module.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
        @Override
        public void serialize(
            LocalDateTime localDateTime, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
          jsonGenerator
              .writeString(
                  DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss").format(localDateTime));
        }
      });
      module.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
          return LocalDateTime.parse(jsonParser.getValueAsString(),
              DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss"));
        }
      });
      mapper.registerModule(module);

      MessageQue messageQue = MessageQue.builder().resDate(LocalDateTime.now()).build();
      String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageQue);
      System.out.println(json);
      MessageQue json2MessageQue = mapper.readValue(json, MessageQue.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}