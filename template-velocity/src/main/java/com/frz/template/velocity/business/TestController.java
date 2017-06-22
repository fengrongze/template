package com.frz.template.velocity.business;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fengrongze on 2017/6/21.
 */
@Controller
public class TestController {


    @RequestMapping("/hello")
    public String hello(ModelMap modelMap){
        modelMap.put("host", "使用Velocity!");
        return "index";
    }

}
