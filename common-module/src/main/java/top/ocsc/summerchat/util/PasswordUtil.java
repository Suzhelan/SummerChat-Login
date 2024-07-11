package top.ocsc.summerchat.util;

import cn.dev33.satoken.secure.SaSecureUtil;

public class PasswordUtil {
    /**
     * 对密码进行加密
     */
    public static String encryptedPassword(String password) {
        return SaSecureUtil.sha1(password);
    }

    /**
     * 检查密码是否只包含英文字母和数字以及标点符号，必须包含数字和英文字符，长度在8-16之间
     */
    public static boolean checkPassword(String password) {
        return password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~]{8,16}$");
    }
}
