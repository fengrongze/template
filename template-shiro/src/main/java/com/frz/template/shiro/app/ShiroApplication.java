package com.frz.template.shiro.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by fengrongze on 2017/6/22.
 */
@SpringBootApplication(scanBasePackages = "com.frz.template.shiro")
public class ShiroApplication {


    public static void main(String[] args) {
        SpringApplication.run(ShiroApplication.class,args);
    }
}
