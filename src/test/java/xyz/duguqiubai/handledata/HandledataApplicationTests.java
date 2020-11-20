package xyz.duguqiubai.handledata;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.duguqiubai.handledata.entity.RNUpdateENtity;
import xyz.duguqiubai.handledata.utils.RNListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
class HandledataApplicationTests {

    @Test
    void contextLoads() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\ttlx\\Desktop\\RN.xls");

        EasyExcel.read("C:\\Users\\ttlx\\Desktop\\RN.xls",RNUpdateENtity.class,new RNListener()).sheet().doRead();
    }

}
