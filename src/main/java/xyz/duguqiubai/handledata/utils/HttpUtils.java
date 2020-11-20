package xyz.duguqiubai.handledata.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * 用java原生的API发送Http请求
 */

public class HttpUtils {

    // jackson里面用来进行json转换的工具
    public static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 无参数post
     *
     * @param uri     地址
     * @param charset 编码
     * @return 字符串
     */
    public static String sendPost(String uri, String charset) {
        String result = null;
        InputStream in = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setRequestMethod("POST");
            // 获取连接
            urlcon.connect();
            urlcon.setAllowUserInteraction(true);

            // 获取返回的数据
            in = urlcon.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in, charset));
            StringBuffer bs = new StringBuffer();
            String line = null;
            while ((line = buffer.readLine()) != null) {
                bs.append(line);
            }
            result = bs.toString();
        } catch (Exception e) {
            System.out.println("[请求异常][地址：" + uri + "][错误信息：" + e.getMessage() + "]");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (Exception e2) {
                System.out.println("[关闭流异常][错误信息：" + e2.getMessage() + "]");
            }
        }
        return result;
    }


    /**
     * 嘎嘎 下面有main方法
     *
     * @param uri
     * @param charset
     * @return
     */
    public static String sendPost(String uri, String charset, Map<String, Object> bodyMap, Map<String, String> headerMap) {
        String result = null;
        PrintWriter out = null;
        InputStream in = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            // 默认是true
            urlcon.setDoInput(true);
            urlcon.setDoOutput(true);
            urlcon.setUseCaches(false);
            urlcon.setRequestMethod("POST");

            // 设置请求头参数
            if (!headerMap.isEmpty()) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    urlcon.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // 获取连接
            urlcon.connect();

            // 请求体里的内容转成json用输出流发送到目标地址
            out = new PrintWriter(urlcon.getOutputStream());
            out.print(objectMapper.writeValueAsString(bodyMap));
            out.flush();

            // 获取返回的数据
            in = urlcon.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in, charset));
            StringBuffer bs = new StringBuffer();
            String line = null;
            while ((line = buffer.readLine()) != null) {
                bs.append(line);
            }
            result = bs.toString();
            System.out.println(result);


        } catch (Exception e) {
            System.out.println("[请求异常][地址：" + uri + "][错误信息：" + e.getMessage() + "]");
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (Exception e2) {
                System.out.println("[关闭流异常][错误信息：" + e2.getMessage() + "]");
            }
        }
        return result;
    }


    public static void main(String[] args) {
        String uri = "http://172.16.3.74:8080/ajax/script/rn_bundle/update";
        String charset = "UTF-8";
        //请求体设置
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("xxxxxx", "1");
        //请求头设置
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("token", "ASDF304IXK2WCQVBM21WN4F35OU6QV0");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("abc", "1");
        //调用
        sendPost(uri, charset, bodyMap, headerMap);
    }

}
