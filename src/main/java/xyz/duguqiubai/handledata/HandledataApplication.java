package xyz.duguqiubai.handledata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.duguqiubai.handledata.spring.DynamicEnvironmentPostProcessor;

@SpringBootApplication
public class HandledataApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HandledataApplication.class);
        // 增加那个监听器动态的处理配置文件
        app.addListeners(new DynamicEnvironmentPostProcessor());
        app.run(args);
    }

}
