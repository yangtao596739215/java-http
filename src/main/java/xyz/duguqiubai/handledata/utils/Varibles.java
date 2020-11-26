package xyz.duguqiubai.handledata.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 将spring配置文件中的值赋给静态变量 方便动态的修改
 */

@Component
public class Varibles implements InitializingBean {

    public static String SESSION ;

    public static String rnLoginUrl;

    public static String rnUpdateURL;

    @Value("${rn.session}")
    private String session;

    @Override
    public void afterPropertiesSet() throws Exception {
        SESSION = session;
    }

    @Value("${rn.loginUrl}")
    public void setRnLoginUrl(String url){
        rnLoginUrl = url;
    }

    @Value("${rn.updateURL}")
    public void setRnUpdateURL(String url){
        rnUpdateURL = url;
    }

}
