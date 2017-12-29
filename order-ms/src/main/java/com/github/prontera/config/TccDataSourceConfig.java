package com.github.prontera.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
//@MapperScan(basePackages = TccDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "tccSqlSessionFactory")
public class TccDataSourceConfig {

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "";
    static final String MAPPER_LOCATION = "";

    @Value("${tccDataSource.datasource.url}")
    private String url;

    @Value("${tccDataSource.datasource.username}")
    private String user;

    @Value("${tccDataSource.datasource.password}")
    private String password;

    @Value("${tccDataSource.datasource.driverClassName}")
    private String driverClass;

    @Bean(name = "tccDataSource")
    public DataSource tccDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "springJdbcTransactionRepository")
    public SpringJdbcTransactionRepository springJdbcTransactionRepository() throws PropertyVetoException {
        SpringJdbcTransactionRepository springJdbcTransactionRepository = new SpringJdbcTransactionRepository();
        springJdbcTransactionRepository.setDataSource(tccDataSource());
        springJdbcTransactionRepository.setDomain("order");
        springJdbcTransactionRepository.setTbSuffix("_ord");
        return springJdbcTransactionRepository;
    }

//    @Bean(name = "tccSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("tccDataSource") DataSource tccDataSource)
//            throws Exception {
//        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(tccDataSource);
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources(TccDataSourceConfig.MAPPER_LOCATION));
//        return sessionFactory.getObject();
//    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
