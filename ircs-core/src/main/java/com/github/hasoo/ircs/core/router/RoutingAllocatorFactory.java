package com.github.hasoo.ircs.core.router;

public class RoutingAllocatorFactory {
  OrderRoutingAllocator orderRoutigAllocator = new OrderRoutingAllocator();

  public RoutingAllocator get(String routingType) {
    if (routingType.equalsIgnoreCase("order")) {
      return this.orderRoutigAllocator;
    } else {
      return null;
    }
  }
}
