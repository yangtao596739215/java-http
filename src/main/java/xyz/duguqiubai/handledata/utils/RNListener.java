package xyz.duguqiubai.handledata.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.duguqiubai.handledata.entity.RNUpdateENtity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RNListener extends AnalysisEventListener<RNUpdateENtity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RNListener.class);
    private final List<RNUpdateENtity> data = new ArrayList<>();

    /**
     * 解析每条数据时都会调用
     */
    @Override
    public void invoke(RNUpdateENtity rnUpdateENtity, AnalysisContext context) {
        data.add(rnUpdateENtity);
        System.out.println(rnUpdateENtity);
        System.out.println("**************************************");

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("appCode", rnUpdateENtity.getAppCode());
        requestBody.put("minVersion", rnUpdateENtity.getMinVersion());
        requestBody.put("rnBundleVersion", rnUpdateENtity.getVersion());
        requestBody.put("platform", rnUpdateENtity.getPlatform() + "");
        requestBody.put("developerDesc", rnUpdateENtity.getDeveloperDesc());

        HashMap<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Cookie", "SESSION=a0415630-3035-4dee-a0f9-2dc148c71456");
        requestHeader.put("Content-Type", "application/x-www-form-urlencoded");
        requestHeader.put("Accept", "application/json");

        String result = HttpUtils.sendPost("http://localhost:8080/ajax/script/rn_bundle/update"
                , "UTF-8"
                , requestBody
                , requestHeader
        );
        System.out.println("返回的返回体中的结果为:"+result);


    }

    /**
     * 所有数据解析完之后调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 可以在此处执行业务操作
        // 本例就打印到控制台即可，表示读取完成
        System.out.println("读取完成");
    }
}
