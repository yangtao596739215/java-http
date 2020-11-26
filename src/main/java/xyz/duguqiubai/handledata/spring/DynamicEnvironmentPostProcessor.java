package xyz.duguqiubai.handledata.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class DynamicEnvironmentPostProcessor implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private  Map<String, OriginTrackedValue> source;


    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        // 打断点查看，这里面包含了很多的信息，包括系统信息和配置文件的信息，一个配置文件对应一个
        MutablePropertySources propertySources = event.getEnvironment().getPropertySources();

        // 循环 每一个propertySource 对应一个配置文件
        for (PropertySource<?> propertySource : propertySources) {
            boolean applicationConfig = propertySource.getName().contains("applicationConfig");
            if (!applicationConfig) {
                continue;
            }
            // 取到这个key对应的value
            Object property = propertySource.getProperty("destination");

            // 得到所有的key和value
            Map<String, OriginTrackedValue> source = (Map<String, OriginTrackedValue>) propertySource.getSource();
            setSource(source);
            OriginTrackedValue originTrackedValue = source.get("destination");
            OriginTrackedValue newOriginTrackedValue = OriginTrackedValue.of("中国香港", originTrackedValue.getOrigin());
            source.put("destination", newOriginTrackedValue);
            System.out.println(property);
        }
    }

    public Map<String, OriginTrackedValue> getSource() {
        return source;
    }

    public void setSource(Map<String, OriginTrackedValue> source) {
        this.source = source;
    }
}
