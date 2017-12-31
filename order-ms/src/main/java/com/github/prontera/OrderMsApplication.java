package com.github.prontera;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mengyun.tcctransaction.spring.EnableTccTransaction;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


//@EnableTccTransaction
//@EnableAspectJAutoProxy

@EnableFeignClients
@SpringCloudApplication
@MapperScan(basePackages = "com.github.prontera.persistence", annotationClass = MyBatisRepository.class)
public class OrderMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderMsApplication.class, args);
    }

//    @Bean
//    @Profile("db")
//    public DataSource tccDataSource() throws Exception {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass("com.mysql.jdbc.Driver");
//        dataSource.setJdbcUrl("jdbc:mysql://rm-uf64h3g1yat0070cvo.mysql.rds.aliyuncs.com:3306/TCC?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true");
//        dataSource.setUser("root");
//        dataSource.setPassword("!yumama666");
//        return dataSource;
//    }
//
//    @Bean
//    @Profile("db")
//    public SpringJdbcTransactionRepository springJdbcTransactionRepository() throws Exception {
//        SpringJdbcTransactionRepository springJdbcTransactionRepository = new SpringJdbcTransactionRepository();
//        springJdbcTransactionRepository.setDataSource(tccDataSource());
//        springJdbcTransactionRepository.setDomain("order");
//        springJdbcTransactionRepository.setTbSuffix("_ord");
//        return springJdbcTransactionRepository;
//    }
//
//    @Bean
//    public DataSource dataSource() throws Exception {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass("com.mysql.jdbc.Driver");
//        dataSource.setJdbcUrl("jdbc:mysql://rm-uf64h3g1yat0070cvo.mysql.rds.aliyuncs.com:3306/TCC_ORD?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true");
//        dataSource.setUser("root");
//        dataSource.setPassword("!yumama666");
//        return dataSource;
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() throws Exception {
//        return new JdbcTemplate(dataSource());
//    }
//
//    @Bean
//    public DataSourceTransactionManager transactionManager() throws Exception {
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
//        transactionManager.setDataSource(dataSource());
//        return transactionManager;
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper();
//    }
}
