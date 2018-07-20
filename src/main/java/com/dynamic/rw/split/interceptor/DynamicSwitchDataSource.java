package com.dynamic.rw.split.interceptor;

import com.dynamic.rw.split.annotation.DynamicDataSource;
import com.dynamic.rw.split.constant.DbOperation;
import com.dynamic.rw.split.constant.MethodStarter.MethodOperationMapper;
import com.dynamic.rw.split.constant.RoutingKeyConst;
import com.dynamic.rw.split.route.MultiDataSourceRouter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component(value = "dynamicSwitchDataSource")
@Order(value = 1)
public class DynamicSwitchDataSource {

  @Autowired
  private MultiDataSourceRouter multiDataSourceRouting;

  @Pointcut(value = "@annotation(dynamic)")
  public void dbOperatePointCut(DynamicDataSource dynamic) {
  }

  @Before("dbOperatePointCut(dynamic)")
  public void doSwitch(DynamicDataSource dynamic) {
    DbOperation dbOperation = dynamic.value();
    dynamicConfig(dbOperation);
  }

  @Pointcut(value = "@annotation(com.dynamic.rw.split.annotation.AutoChoose)")
  public void autoChoose() {
  }

  @Before("autoChoose()")
  public void doSwitch(ProceedingJoinPoint pjp) {
    String name = pjp.getSignature().getName();
    DbOperation dbOperation = MethodOperationMapper.getDbOperationMapper(name);
    if (dbOperation == null) {
      throw new IllegalArgumentException("The starting for method is illegal");
    }
    dynamicConfig(dbOperation);
  }

  private void dynamicConfig(DbOperation dbOperation) {
    switch (dbOperation) {
      case READ:
        multiDataSourceRouting.setRouteKey(RoutingKeyConst.SLAVE);
        break;
      case WRITE:
        multiDataSourceRouting.setRouteKey(RoutingKeyConst.MASTER);
        break;
    }
  }
}
