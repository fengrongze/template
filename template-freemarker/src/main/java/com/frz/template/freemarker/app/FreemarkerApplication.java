package com.frz.template.freemarker.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by fengrongze on 2017/6/28.
 */
@SpringBootApplication(scanBasePackages = "com.frz.template")
@Async
public class FreemarkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreemarkerApplication.class,args);
    }
}
