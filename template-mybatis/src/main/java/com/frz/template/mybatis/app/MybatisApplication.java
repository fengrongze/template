package com.frz.template.mybatis.app;

import com.frz.template.mybatis.business.entity.BankUserInfo;
import com.frz.template.mybatis.business.entity.City;
import com.frz.template.mybatis.business.service.CityService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.Properties;


/**
 * Created by fengrongze on 2017/6/21.
 */
@SpringBootApplication(scanBasePackages = "com.frz.template.mybatis")
@MapperScan("com.frz.template.mybatis.business.mapper")
@EnableAsync
public class MybatisApplication {



    @Bean
    @ConfigurationProperties(prefix = "tomcat.datasource")
    public PoolConfiguration getPoolProperties() {
        return new PoolProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "tomcat.datasource")
    public javax.sql.DataSource dataSource() {
        PoolConfiguration poolProperties = getPoolProperties();
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
        props.setProperty("reasonable", "true");
        props.setProperty("supportMethodsArguments", "true");
        props.setProperty("returnPageInfo", "check");
        props.setProperty("params", "count=countSql");
        pageHelper.setProperties(props);
        //添加插件
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }


    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(MybatisApplication.class,args);

        CityService cityService = context.getBean(CityService.class);
        List<City> cityList = cityService.queryCity();
        if(CollectionUtils.isEmpty(cityList)){
            System.out.println("未查询到结果");
        }else{
            for (int i = 0; i <cityList.size() ; i++) {
                System.out.println(cityList.get(i).getId()+" ,"+cityList.get(i).getName()+" ,"+cityList.get(i).getState());
            }
        }

        BankUserInfo bankUserInfo=cityService.queryUser();

        System.out.println(bankUserInfo.getUserName());
    }


}
