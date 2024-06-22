package top.ocsc.summerchat.login.service;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ocsc.summerchat.dao.UserDao;
import top.ocsc.summerchat.entity.User;
import top.ocsc.summerchat.result.CommonResult;
import top.ocsc.summerchat.util.PasswordUtil;

import java.util.Map;

@Service
public class UserLoginService {
    @Autowired
    UserDao userDao;

    public CommonResult loginByUin(String uin, String password, Map<String, Object> extraInfo) {
        User user = userDao.queryByUin(Long.parseLong(uin));
        if (user == null) {
            return CommonResult.error("User does not exist");
        }
        String encryptedPassword = PasswordUtil.encryptedPassword(password);
        if (!user.getEncryptedPassword().equals(encryptedPassword)) {
            return CommonResult.error("wrong password");
        }
        StpUtil.login(uin, new SaLoginModel()
                .setDevice((String) extraInfo.get("device"))
                .setIsWriteHeader(false)
                .setIsLastingCookie(true));
        return CommonResult.ok("login successful").setData(StpUtil.getTokenInfo());
    }

    public CommonResult loginStatus() {
        try {
            StpUtil.getLoginId();
            return CommonResult.ok("Login status is normal");
        } catch (NotLoginException nle) {
            // 判断场景值，定制化异常信息
            String message;
            if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
                message = "failed to read valid token";
            } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
                message = "token invalid";
            } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
                message = "token expired";
            } else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
                message = "token has been pushed offline";
            } else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
                message = "token has been kicked offline";
            } else if (nle.getType().equals(NotLoginException.TOKEN_FREEZE)) {
                message = "token has been frozen";
            } else if (nle.getType().equals(NotLoginException.NO_PREFIX)) {
                message = "the token was not submitted according to the specified prefix";
            } else {
                message = "not logged in for the current session";
            }
            // 返回给前端
            return CommonResult.error(message)
                    .setAction(Integer.parseInt(nle.getType()));
        }
    }

}
