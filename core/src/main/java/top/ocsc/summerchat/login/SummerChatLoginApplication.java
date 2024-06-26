package top.ocsc.summerchat.login;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("top.ocsc.summerchat")
@MapperScan("top.ocsc.summerchat")
public class SummerChatLoginApplication {


    public static void main(String[] args) {
        SpringApplication.run(SummerChatLoginApplication.class, args);
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }

}
