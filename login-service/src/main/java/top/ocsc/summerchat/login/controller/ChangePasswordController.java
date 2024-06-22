package top.ocsc.summerchat.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ocsc.summerchat.login.service.ChangePasswordService;
import top.ocsc.summerchat.result.CommonResult;

@RestController
public class ChangePasswordController {
    @Autowired
    public ChangePasswordService changePasswordService;

    /**
     * 更改密码
     *
     * @param email            邮箱
     * @param password         新密码
     * @param verificationCode 邮箱收到的修改密码验证码
     */
    @PostMapping("/changePassword")
    public CommonResult changeUserPassword(String email, String password, String verificationCode) {
        return changePasswordService.changePassword(email, password, verificationCode);
    }

    /**
     * 申请找回密码
     *
     * @param email 接受修改密码验证码的邮箱
     */
    @PostMapping("/applyToChangeAccountPassword")
    public CommonResult applyToChangeAccountPassword(String email) {
        return changePasswordService.applyToChangeAccountPassword(email);
    }
}
