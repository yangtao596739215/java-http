package xyz.duguqiubai.handledata.spring;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MyEnvironmentAware implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment env) {
        System.out.println("lalal");
    }
}
