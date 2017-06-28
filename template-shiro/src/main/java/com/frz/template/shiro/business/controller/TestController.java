package com.frz.template.shiro.business.controller;

import com.frz.template.shiro.bean.vo.User;
import com.frz.template.shiro.config.ShiroConfiguration;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Created by fengrongze on 2017/6/27.
 */
@Controller
public class TestController {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ShiroConfiguration.class);

    @RequestMapping(value = "/login",method={RequestMethod.GET})
    public String login(Model model){
        model.addAttribute("user", new User());

        return "login";
    }


    @RequestMapping(value = "/login",method={RequestMethod.POST})
    public String loginIn(Model model, User user, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "login";
        }

        String username = user.getUsername();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
       //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();

        model.addAttribute("user",user );
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        }catch(UnknownAccountException uae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
        }catch(IncorrectCredentialsException ice){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        }catch(LockedAccountException lae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        }catch(ExcessiveAttemptsException eae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        //验证是否登录成功
        if(currentUser.isAuthenticated()){
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            return "redirect:/user";
        }else{
            token.clear();
            return "redirect:/login";
        }
    }


    @RequestMapping(value = "/user")
    public String user(Model model,String username){
        System.out.println("提交完成");
        return "info";
    }

    @RequestMapping(value = "/user/edit")
    public String userEdit(Model model,String username){
        System.out.println("提交完成");
        return "user";
    }

    @RequestMapping("/my/error")
    public String error(ModelMap modelMap){
        System.out.println("错误");
        modelMap.put("host", "异常页面");
        return "index";
    }



    @RequestMapping("/")
    public String hello(ModelMap modelMap){
        modelMap.put("host", "首页");
        return "index";
    }
}
