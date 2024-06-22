package top.ocsc.summerchat.login.service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
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
            return CommonResult.error("用户不存在");
        }
        String encryptedPassword = PasswordUtil.encryptedPassword(password);
        if (!user.getEncryptedPassword().equals(encryptedPassword)) {
            return CommonResult.error("密码错误");
        }
        StpUtil.login(uin, new SaLoginModel()
                .setDevice((String) extraInfo.get("device"))
                .setIsWriteHeader(false)
                .setIsLastingCookie(true));
        SaSession session = StpUtil.getSessionByLoginId(uin);
//        extraInfo.forEach(session::set);
        return CommonResult.ok("登录成功")
                .setData(session.getTokenSignList());
    }

    public CommonResult loginStatus() {
        return CommonResult.ok()
                .set("isLogin", StpUtil.isLogin());
    }

}
