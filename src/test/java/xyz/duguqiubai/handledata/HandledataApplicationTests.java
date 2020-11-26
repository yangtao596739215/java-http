package xyz.duguqiubai.handledata;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.duguqiubai.handledata.entity.RNUpdateENtity;
import xyz.duguqiubai.handledata.spring.DynamicEnvironmentPostProcessor;
import xyz.duguqiubai.handledata.utils.RNListener;
import xyz.duguqiubai.handledata.utils.Varibles;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class HandledataApplicationTests {

    // jackson里面用来进行json转换的工具
    public static ObjectMapper objectMapper = new ObjectMapper();

    @Value("${rn.session}")
    private String env;
    @Value("${rn.loginUrl}")
    private String loginUrl;


    @Autowired
    private DynamicEnvironmentPostProcessor dynamicEnvironmentPostProcessor;

    @Test
    void contextLoads() throws FileNotFoundException {
        try {
            getSession();
        } catch (Exception e) {
            System.out.println("获取cookie出错了");
            return;
        }
        EasyExcel.read("C:\\Users\\ttlx\\Desktop\\RN.xls", RNUpdateENtity.class, new RNListener()).sheet().doRead();
    }

    @Test
    public void getSession() throws Exception {
        System.out.println(env);
        if (" ".equals(Varibles.SESSION))
            Varibles.SESSION = "lala";

        PrintWriter out = null;
        BufferedReader br = null;

        URL url = new URL(loginUrl);
        HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
        // 默认是true
        urlcon.setDoInput(true);
        urlcon.setDoOutput(true);
        urlcon.setUseCaches(false);
        urlcon.setRequestMethod("POST");

        // 设置请求头
        urlcon.setRequestProperty("Content-Type", "application/json");

        // 构建请求体参数
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("token", "tiantian6");

        // 获取连接
        urlcon.connect();

        // 发送数据
        out = new PrintWriter(urlcon.getOutputStream());
        out.write(objectMapper.writeValueAsString(bodyMap));
        out.flush();

        System.out.println("返回码为" + urlcon.getResponseCode());
        System.out.println("返回的信息为" + urlcon.getResponseMessage());

        // 获取返回头里的信息
        Map<String, List<String>> headerFields = urlcon.getHeaderFields();
        System.out.println(headerFields);
        System.out.println(headerFields.get("Set-Cookie"));
        Varibles.SESSION = headerFields.get("Set-Cookie").get(0);
        urlcon.disconnect();

        // 这里只是获取返回头里面的信息，后面的信息就不需要传了

        // 这里注意了，正常返回的输入流和错误返回的输入流不是一个
//        InputStream is;
//        if (urlcon.getResponseCode() >= 400) {
//            is = urlcon.getErrorStream();
//        } else {
//            is = urlcon.getInputStream();
//        }
//        //取得输入流，并使用Reader读取
//        br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
//        String line;
//        // readLine()为阻塞方法，会等待服务器的响应，否则会一直等待
//        while ((line = br.readLine()) != null) {
//            result.append(line);
//            System.out.println("line:" + line);
//            result.append("\r\n");
//        }
    }

    public void printSession() {
        System.out.println(Varibles.SESSION);
    }


    @Test
    public void test() {
//        getSession();
//        printSession();
        System.out.println(env);
        Map<String, OriginTrackedValue> source = dynamicEnvironmentPostProcessor.getSource();
        OriginTrackedValue myname = source.get("rn.session");
        OriginTrackedValue newOriginTrackedValue = OriginTrackedValue.of("中国香港", myname.getOrigin());
        source.put("rn.session", newOriginTrackedValue);
        System.out.println(env);

    }

    @Test
    public void testparam() {

        StringBuilder result = new StringBuilder();
        PrintWriter out = null;
        BufferedReader br = null;
        HttpURLConnection urlcon = null;
        try {
            URL url = new URL("http://localhost:8888/test");
            urlcon = (HttpURLConnection) url.openConnection();
            // 默认是true
            urlcon.setDoInput(true);
            urlcon.setDoOutput(true);
            urlcon.setUseCaches(false);
            urlcon.setRequestMethod("POST");

            // 设置请求头参数
            urlcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlcon.setRequestProperty("Accept", "*/*");
            // 同样使用URLencode转码，这种post格式跟get的区别在于，get把转换、拼接完的字符串用‘?’直接与表单的action连接作为URL使用，
            // 所以请求体里没有数据；而post把转换、拼接后的字符串放在了请求体里，不会在浏览器的地址栏显示，因而更安全一些

            // 获取连接
            urlcon.connect();

            // 模拟提交表单数据, 以key和value的形式，通过&分割  请求头设置为"Content-Type", "application/x-www-form-urlencoded"
            // 后端用 @requestparam来接受
            out = new PrintWriter(urlcon.getOutputStream());
            out.write("content=wqwww&name=lala");
            out.flush();

            // 这种写法用request body来接受   而且请求头应该设置为 Content-Type，application/json
            // 把对象转成json字符串来传送
            // out.write(objectMapper.writeValueAsString(bodyMap));
            // 后端用 @RequestBody来接受
            // 以json传的好处在于可以传大量的数据，而不需要以key-value的形式一个一个去拼接

            System.out.println(urlcon.getResponseCode());
            System.out.println(urlcon.getResponseMessage());

            // 获得输入流  这里注意了，正常返回的输入流和错误返回的输入流不是一个,虽然都是从请求体中拿的
            InputStream is;
            if (urlcon.getResponseCode() >= 400) {
                is = urlcon.getErrorStream();
            } else {
                is = urlcon.getInputStream();
            }

            // 使用reader从输入流中获取数据
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != br) {
                    br.close();
                }
                if (null != out) {
                    out.close();
                }
                assert urlcon != null;
                urlcon.disconnect();
            } catch (Exception e2) {
                System.out.println("[关闭流异常][错误信息：" + e2.getMessage() + "]");
            }
        }
        System.out.println(result);
    }

}
