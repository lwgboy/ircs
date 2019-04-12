package com.github.hasoo.ircs.core.router;

import com.github.hasoo.ircs.core.router.map.ClientRoutingMap;
import com.github.hasoo.ircs.core.router.map.Routing;
import com.github.hasoo.ircs.core.router.map.RoutingMap;
import com.github.hasoo.ircs.core.router.map.RoutingMapper;
import com.github.hasoo.ircs.core.router.map.Sender;
import java.util.ArrayList;
import java.util.List;
import com.github.hasoo.ircs.core.queue.SenderQue;

public class Router {
  private ClientRoutingMap clientRoutingMap = null;
  private RoutingMap routingMap = null;
  private RoutingMapper routingMapper = null;
  private RoutingAllocatorFactory routingAllocatorFactory = new RoutingAllocatorFactory();

  @SuppressWarnings("unused")
  private Router() {}

  public Router(RoutingMapper routingMapper) {
    this.routingMapper = routingMapper;
  }

  public synchronized void setupClientRouting() {
    this.clientRoutingMap = routingMapper.loadClientRouting()
        .orElseThrow(() -> new RuntimeException("failed to load clientRoutingMap"));
  }

  public synchronized void setupRouting() {
    this.routingMap = routingMapper.loadRouting()
        .orElseThrow(() -> new RuntimeException("failed to load routingMap"));
  }

  public synchronized String route(SenderQue que) {
    RoutingAllocator routingAllocator = null;
    if (null == que.getRoutingType()) {
      String routingName = clientRoutingMap.getRoutingName(que.getUsername()).orElse("default");
      Routing routing = routingMap.getRouting(routingName).orElseThrow(
          () -> new RuntimeException(String.format("unknown routingName(%s)", routingName)));


      routingAllocator = routingAllocatorFactory.get(routing.getType());
      List<Sender> senders = routing.getSender(que.getMsgType())
          .orElseThrow(() -> new RuntimeException("failed to load clientRoutingMap"));

      que.setRoutingType(routing.getType());
      que.setSenders(new ArrayList<Sender>(senders));
    } else {
      routingAllocator = routingAllocatorFactory.get(que.getRoutingType());
    }

    return routingAllocator.getRoutingKey(que);
  }

  // public void loadRoutingMapProperty() {
  // List<Sender> smsSenders = new ArrayList<Sender>();
  //
  // smsSenders.add(new Sender("SKT_1", "SKT", 1));
  // smsSenders.add(new Sender("KT_1", "KT", 3));
  // smsSenders.add(new Sender("LGT_1", "LGT", 2));
  //
  // List<Sender> lmsSenders = new ArrayList<Sender>();
  //
  // lmsSenders.add(new Sender("SKTLMS_1", "SKT", 3));
  // lmsSenders.add(new Sender("KTLMS_1", "KT", 1));
  // lmsSenders.add(new Sender("LGTLMS_1", "LGT", 2));
  //
  // List<Sender> mmsSenders = new ArrayList<Sender>();
  //
  // mmsSenders.add(new Sender("SKTMMS_1", "SKT", 2));
  // mmsSenders.add(new Sender("KTMMS_1", "KT", 1));
  // mmsSenders.add(new Sender("LGTMMS_1", "LGT", 3));
  //
  // HashMap<String, List<Sender>> senders = new HashMap<>();
  // senders.put("SMS", smsSenders);
  // senders.put("LMS", lmsSenders);
  // senders.put("MMS", mmsSenders);
  //
  // Routing telecomRouting = new Routing("telecom", senders);
  // Routing orderRouting = new Routing("order", senders);
  //
  // HashMap<String, Routing> routings = new HashMap<>();
  // routings.put("real", telecomRouting);
  // routings.put("batch", orderRouting);
  // routings.put("adv", orderRouting);
  //
  // RoutingMap routingMap = new RoutingMap(routings);
  //
  // HashMap<String, String> clientRoutings = new HashMap<>();
  // clientRoutings.put("test1", "real");
  // clientRoutings.put("test2", "batch");
  //
  // ClientRoutingMap clientRoutingMap = new ClientRoutingMap(clientRoutings);
  //
  // try {
  // ObjectMapper objectMapper = new ObjectMapper();
  // log.info(objectMapper.writeValueAsString(clientRoutingMap));
  // log.info(objectMapper.writeValueAsString(routingMap));
  // } catch (JsonProcessingException e) {
  // e.printStackTrace();
  // }
  // }
}
