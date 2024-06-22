package top.ocsc.summerchat.login.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.ocsc.summerchat.login.service.UserLoginService;
import top.ocsc.summerchat.result.CommonResult;

@RestController
public class UserLoginController {
    @Autowired
    UserLoginService loginService;

    @PostMapping("login")
    public CommonResult login(@RequestParam("uin") String uin, @RequestParam("password") String password, @RequestParam(value = "extraData", required = false) String extraData) {
        return loginService.loginByUin(uin, password, JSON.parseObject(extraData));
    }

    @PostMapping("isLogin")
    public CommonResult isLogin() {
        return loginService.loginStatus();
    }

}
