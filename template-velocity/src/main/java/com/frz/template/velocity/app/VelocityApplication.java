package com.frz.template.velocity.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by fengrongze on 2017/6/21.
 */
@SpringBootApplication(scanBasePackages = "com.frz.template.velocity")
@EnableAsync
public class VelocityApplication {


    public static void main(String[] args) {
        SpringApplication.run(VelocityApplication.class, args);
    }

}
