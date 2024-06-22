package top.ocsc.summerchat.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ocsc.summerchat.dao.UserDao;
import top.ocsc.summerchat.entity.User;
import top.ocsc.summerchat.redis.RedisKeyPrefix;
import top.ocsc.summerchat.redis.RedisUtil;
import top.ocsc.summerchat.result.CommonResult;
import top.ocsc.summerchat.service.EmailSendService;
import top.ocsc.summerchat.util.PasswordUtil;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 修改、找回密码服务
 */
@Service
public class ChangePasswordService {

    @Autowired
    private EmailSendService emailSendService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisUtil<String, String> redisUtil;

    /**
     * 更改密码
     *
     * @param email
     * @param password
     * @param verificationCode
     * @return
     */
    public CommonResult changePassword(String email, String password, String verificationCode) {
        String key = RedisKeyPrefix.SUMMERCHAT_CHANGE_PASSWORD_VERIFICATION_CODE + email;
        String code = redisUtil.getCacheValue(key);
        if (code == null) {
            return CommonResult.error("verification code has expired")
                    .setAction(-1);
        }
        if (!code.equals(verificationCode)) {
            return CommonResult.error("verification code error")
                    .setAction(-2);
        }
        //先查询是否已经被注册
        User user = userDao.queryByEmail(email);
        //没注册 返回结果
        if (user == null) {
            return CommonResult.error("this email is not registered")
                    .setAction(-3);
        }
        user.setEmail(email);
        user.setEncryptedPassword(PasswordUtil.encryptedPassword(password));
        userDao.update(user);
        //注册逻辑
        return CommonResult.ok("password has been updated")
                .set("uin", user.getUin());
    }

    /**
     * 申请更改密码
     *
     * @param email 邮箱
     * @return
     */
    public CommonResult applyToChangeAccountPassword(String email) {
        String key = RedisKeyPrefix.SUMMERCHAT_CHANGE_PASSWORD_VERIFICATION_CODE + email;
        if (redisUtil.hasKey(key)) {
            Long expire = redisUtil.getExpire(key, TimeUnit.SECONDS);
            return CommonResult.ok()
                    .setMsg("The verification code has been sent. Please check it.")
                    .set("email", email)
                    .set("expire", expire);//验证码过期时间
        }
        User user = userDao.queryByEmail(email);
        //邮箱没注册
        if (user == null) {
            return CommonResult.error("this email is not registered")
                    .setAction(-1);
        }
        //生成随机验证码
        Random random = new Random();
        int number = random.nextInt(900000) + 100000;
        String randomString = Integer.toString(number);
        //发送邮件
        emailSendService.sendRegistrationVerificationCodeEmail(email, "申请找回密码", randomString);
        //将验证码存入redis 设置过期时间为20分钟
        redisUtil.setCacheValue(key, randomString, 20, TimeUnit.MINUTES);

        return CommonResult.ok("verification code has been sent to email")
                .set("email", email)
                .set("expire", redisUtil.getExpire(key, TimeUnit.SECONDS));
    }
}
