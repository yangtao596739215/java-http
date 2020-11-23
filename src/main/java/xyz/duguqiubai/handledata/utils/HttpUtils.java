package xyz.duguqiubai.handledata.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    public static String sendPost(String uri, String charset, Map<String, String> bodyMap, Map<String, String> headerMap) {

        StringBuilder result = new StringBuilder();
        PrintWriter out = null;
        BufferedReader br = null;

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

            // 构建请求体参数
            // 构建请求参数
            StringBuilder sb = new StringBuilder();
            if (!bodyMap.isEmpty()) {
                for (Map.Entry<String, String> e : bodyMap.entrySet()) {
                    sb.append(e.getKey());
                    sb.append("=");
                    sb.append(e.getValue());
                    sb.append("&");
                }
            }

            // 获取连接
            urlcon.connect();
            out = new PrintWriter(urlcon.getOutputStream());
            out.write(sb.toString());
            out.flush();
            System.out.println("请求的参数为："+sb.toString());
            System.out.println("返回码为"+urlcon.getResponseCode());
            System.out.println("返回的信息为"+urlcon.getResponseMessage());

            // 这里注意了，正常返回的输入流和错误返回的输入流不是一个,虽然都是从请求体中拿的
            InputStream is;
            if (urlcon.getResponseCode() >= 400 ) {
                is = urlcon.getErrorStream();
            } else{
                is = urlcon.getInputStream();
            }
            //取得输入流，并使用Reader读取
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            // readLine()为阻塞方法，会等待服务器的响应，否则会一直等待
            while ((line = br.readLine()) != null) {
                result.append(line);
                System.out.println("line:"+line);
                result.append("\r\n");
            }
            urlcon.disconnect();
        } catch (Exception e) {
            System.out.println("[请求异常][地址：" + uri + "][错误信息：" + e.getMessage() + "]");
            e.printStackTrace();

        } finally {

            try {
                if (null != br) {
                    br.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (Exception e2) {
                System.out.println("[关闭流异常][错误信息：" + e2.getMessage() + "]");
            }

        }
        return result.toString();
    }

    public static void main(String[] args) {
        String uri = "http://172.16.3.74:8080/ajax/script/rn_bundle/update";
        String charset = "UTF-8";
        //请求体设置
        HashMap<String, String> bodyMap = new HashMap<>();
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
