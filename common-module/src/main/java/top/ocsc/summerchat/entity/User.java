package top.ocsc.summerchat.entity;

import java.io.Serializable;

/**
 * 用户表(User)实体类
 *
 * @author makejava
 * @since 2024-06-19 17:55:37
 */
public class User implements Serializable {
    private static final long serialVersionUID = -30173898924538545L;
/**
     * 账号
     */
    private Long uin;
/**
     * 密码
     */
    private String encryptedPassword;

    private String email;


    public Long getUin() {
        return uin;
    }

    public void setUin(Long uin) {
        this.uin = uin;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

