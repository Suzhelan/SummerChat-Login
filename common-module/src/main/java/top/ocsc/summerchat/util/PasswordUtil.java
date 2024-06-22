package top.ocsc.summerchat.util;

import cn.dev33.satoken.secure.SaSecureUtil;

public class PasswordUtil {
    public static String encryptedPassword(String password) {
        return SaSecureUtil.sha1(password);
    }
}
