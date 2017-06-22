package com.frz.template.mulisource.config;

import com.frz.template.mulisource.util.QueryJDBCUtils;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/**
 * Created by fengrongze on 2017/6/21.
 */
@Configuration
public class SecondDataSourceConfig {

    @Bean(name ="getQueryPoolProperties")
    @ConfigurationProperties(prefix = "tomcat.query.datasource")
    public PoolConfiguration getQueryPoolProperties() {
        return new PoolProperties();
    }


    @Bean(name = "queryDataSource" )
    @Qualifier("queryDataSource")
    @ConfigurationProperties(prefix = "tomcat.query.datasource")
    public javax.sql.DataSource queryDataSource() {
        PoolConfiguration poolProperties = getQueryPoolProperties();
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
        return dataSource;
    }



    @Bean(name = "queryJdbcTemplate")
    public JdbcTemplate queryJdbcTemplate() {
        return new JdbcTemplate(queryDataSource());
    }

    @Bean(name="queryNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate queryNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(queryDataSource());
    }


    @Bean(name="querySimpleJdbcCall")
    public SimpleJdbcCall querySimpleJdbcCall() {
        return new SimpleJdbcCall(queryJdbcTemplate());
    }



    @Bean(name = "queryJdbcUtils")
    @Qualifier("queryJdbcUtils")
    public QueryJDBCUtils queryJdbcUtils() {
        return new QueryJDBCUtils();
    }


}
