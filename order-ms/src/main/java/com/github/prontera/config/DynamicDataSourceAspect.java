package com.github.prontera.config;


import com.github.prontera.config.dbsource.CustomizeDS;
import com.github.prontera.config.dbsource.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

@Aspect
@Order(-1)
@ComponentScan
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);


    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds) throws Throwable {
        CustomizeDS dsEnum = ds.value();
        logger.debug("method：{} 使用数据源 : {}", point.getSignature(), ds.value());
        DynamicDataSourceContextHolder.setDataSourceType(dsEnum.toString());
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
        logger.debug("method：{} 执行完毕，还原数据源 DataSource : {}", point.getSignature(), ds.value());
        DynamicDataSourceContextHolder.clearDataSourceType();
    }

}
