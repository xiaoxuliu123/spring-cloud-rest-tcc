package com.github.prontera;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.prontera.MyBatisRepository;
import com.github.prontera.config.dbsource.CustomizeDS;
import com.github.prontera.config.dbsource.DynamicDataSourceResolver;
import com.google.common.collect.ImmutableMap;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

@Configuration
//@PropertySource(value="classpath:application.properties")
public class MyBatisConfig {

    @Value("${order.datasource.url}")
    String value;

    @Bean(name = "orderDataSource")
    //@ConfigurationProperties(prefix = "order.datasource")
    public DataSource orderDataSource(){

        DruidDataSource datasource1 = new DruidDataSource();
        datasource1.setUrl("jdbc:mysql://rm-uf64h3g1yat0070cvo.mysql.rds.aliyuncs.com/order?useLegacyDatetimeCode=false&serverTimezone=Asia/Hong_Kong&useSSL=false&?createDatabaseIfNotExist=true");
        datasource1.setUsername("root");
        datasource1.setPassword("!yumama666");
        datasource1.setDriverClassName("com.mysql.jdbc.Driver");

        return datasource1;
    }

    @Bean(name = "tccDataSource")
    //@ConfigurationProperties(prefix = "tccData.datasource")
    public DataSource tccDataSource(){
        DruidDataSource datasource2 = new DruidDataSource();
        datasource2.setUrl("jdbc:mysql://rm-uf64h3g1yat0070cvo.mysql.rds.aliyuncs.com/TCC?useLegacyDatetimeCode=false&serverTimezone=Asia/Hong_Kong&useSSL=false&?createDatabaseIfNotExist=true");
        datasource2.setUsername("root");
        datasource2.setPassword("!yumama666");
        datasource2.setDriverClassName("com.mysql.jdbc.Driver");

        return datasource2;
    }

    //用于动态切换数据源
    @Primary
    @Bean(name="dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("orderDataSource")DataSource orderDataSource
                                                ,@Qualifier("tccDataSource")DataSource tccDataSource){


        System.out.println("121113#&&&&&&&&&&&&&"+value);

        DynamicDataSourceResolver resolver = new DynamicDataSourceResolver();
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(CustomizeDS.ORDER.toString(),orderDataSource);
        dataSources.put(CustomizeDS.TCC.toString(),orderDataSource);
        resolver.setTargetDataSources(dataSources);

        //设置默认数据源--重要
        resolver.setDefaultTargetDataSource(orderDataSource);
        resolver.afterPropertiesSet();
        return resolver;
    }

    //设置SqlSessionFactory,使用动态数据源
    @Bean(name = "dynamicSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("orderDataSource")DataSource orderDataSource
            ,@Qualifier("tccDataSource")DataSource tccDataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(this.dynamicDataSource(orderDataSource,tccDataSource));
        return factory.getObject();
    }

    @Bean
    public MapperScannerConfigurer mappperConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.github.prontera.persistence");
        msc.setSqlSessionFactoryBeanName("dynamicSqlSessionFactory");
        msc.setAnnotationClass(MyBatisRepository.class);
        return msc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
