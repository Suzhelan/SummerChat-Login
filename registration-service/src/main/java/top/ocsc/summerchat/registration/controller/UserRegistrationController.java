package top.ocsc.summerchat.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ocsc.summerchat.registration.service.UserRegistrationService;
import top.ocsc.summerchat.result.CommonResult;


@RestController
public class UserRegistrationController {

    @Autowired
    UserRegistrationService registrationService;

    /**
     * 正式注册一个账号
     *
     * @param email 邮箱
     * @param password 密码
     * @param verificationCode 邮箱收到的注册验证码
     */
    @PostMapping("/register")
    public CommonResult registerUser(String email, String password, String verificationCode) {
        return registrationService.register(email, password, verificationCode);
    }

    /**
     * 申请注册账号
     *
     * @param email 接受注册验证码的邮箱
     */
    @PostMapping("/applyToRegisterAccount")
    public CommonResult applyForRegistration(String email) {
        return registrationService.applyToRegisterAccount(email);
    }

}
