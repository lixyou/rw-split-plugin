package com.dynamic.rw.split.route;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiDataSourceRouter extends AbstractRoutingDataSource {

  public ThreadLocal<String> routeKeyStore = new ThreadLocal<>();

  @Override
  protected Object determineCurrentLookupKey() {
    String routeKey = routeKeyStore.get();
    if (routeKey == null || routeKey.isEmpty()) {
      throw new IllegalArgumentException("please choose master or slave data store!");
    }
    return routeKey;
  }

  public String getRouteKey() {
    return routeKeyStore.get();
  }

  public void setRouteKey(String routeKey) {
    routeKeyStore.set(routeKey);
  }
}
