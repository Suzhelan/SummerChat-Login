package top.ocsc.summerchat.login;

import cn.dev33.satoken.secure.SaSecureUtil;

public class Main {
    public static void main(String[] args) {
        String password = "Linn....06040010";
        System.out.println("byte size:"+password.getBytes().length);
        System.out.println("sha1:"+SaSecureUtil.sha1(password));
        System.out.println("sha1size:"+SaSecureUtil.sha256(password).length());
        System.out.println("md5:"+SaSecureUtil.md5(password));
        System.out.println("md5size:"+SaSecureUtil.md5(password).length());
    }
}
