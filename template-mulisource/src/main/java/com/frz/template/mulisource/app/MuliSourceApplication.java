package com.frz.template.mulisource.app;

import com.frz.template.mulisource.business.entity.BankUserInfo;
import com.frz.template.mulisource.business.entity.City;
import com.frz.template.mulisource.business.service.CityService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

/**
 * Created by fengrongze on 2017/6/21.
 */
@SpringBootApplication(scanBasePackages = "com.frz.template.mulisource")
@EnableAsync
public class MuliSourceApplication {



    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(MuliSourceApplication.class,args);

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

        try {
            cityService.queryTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
