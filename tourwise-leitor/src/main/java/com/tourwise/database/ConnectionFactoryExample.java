package com.tourwise.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/*
public class ConnectionFactory {
    private final JdbcTemplate jdbcTemplate;
    private final BasicDataSource basicDataSource;

    public ConnectionFactory(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql:DB_HOST/DB_NAME");
        basicDataSource.setUsername("DB_USER");
        basicDataSource.setPassword("DB_PASS");

        this.basicDataSource = basicDataSource;
        this.jdbcTemplate = new JdbcTemplate(basicDataSource);
    }

    public BasicDataSource getBasicDataSource(){
        return basicDataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

}
*/