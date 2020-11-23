package xyz.duguqiubai.handledata;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.duguqiubai.handledata.entity.RNUpdateENtity;
import xyz.duguqiubai.handledata.utils.RNListener;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class HandledataApplicationTests {

    @Test
    void contextLoads() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\ttlx\\Desktop\\RN.xls");
        EasyExcel.read("C:\\Users\\ttlx\\Desktop\\RN.xls", RNUpdateENtity.class, new RNListener()).sheet().doRead();
    }

    @Test
    public void testparam() {

        StringBuilder result = new StringBuilder();
        PrintWriter out = null;
        BufferedReader br = null;
        HttpURLConnection urlcon = null ;
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
