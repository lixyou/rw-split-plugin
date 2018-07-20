package com.dynamic.rw.split.constant;

import java.util.HashMap;
import java.util.Map;

public class MethodStarter {

  private static final String FIND = "find";

  private static final String QUERY = "query";

  private static final String SELECT = "select";

  private static final String INSERT = "insert";

  private static final String ADD = "add";


  public static class MethodOperationMapper {

    private static Map<String, DbOperation> mapper = new HashMap<>();

    static {
      mapper.put(FIND, DbOperation.READ);
      mapper.put(QUERY, DbOperation.READ);
      mapper.put(SELECT, DbOperation.READ);
      mapper.put(INSERT, DbOperation.WRITE);
      mapper.put(ADD, DbOperation.WRITE);
    }

    public static DbOperation getDbOperationMapper(String name) {
      return mapper.get(name);
    }
  }
}
