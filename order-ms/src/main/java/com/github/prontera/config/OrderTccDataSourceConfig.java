package com.github.prontera.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
//@MapperScan(basePackages = OrderTccDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "orderTccSqlSessionFactory")
public class OrderTccDataSourceConfig {

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "";
    static final String MAPPER_LOCATION = "";

    @Value("${orderTccDataSource.datasource.url}")
    private String url;

    @Value("${orderTccDataSource.datasource.username}")
    private String user;

    @Value("${orderTccDataSource.datasource.password}")
    private String password;

    @Value("${orderTccDataSource.datasource.driverClassName}")
    private String driverClass;

    @Bean(name = "orderTccDataSource")
    public DataSource orderTccDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate() throws Exception {
        return new JdbcTemplate(orderTccDataSource());
    }

    @Bean(name = "orderTccTransactionManager")
    public DataSourceTransactionManager orderTccTransactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(orderTccDataSource());
    }

//    @Bean(name = "orderTccSqlSessionFactory")
//    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("orderTccDataSource") DataSource masterDataSource)
//            throws Exception {
//        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(masterDataSource);
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources(OrderTccDataSourceConfig.MAPPER_LOCATION));
//        return sessionFactory.getObject();
//    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
