package com.ccxam.demo;

import com.alibaba.excel.EasyExcel;
import com.ccxam.demo.excel.DemoData;
import com.ccxam.demo.excel.ExcleListenner;
import com.ccxam.demo.excel.SunkectData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ExcelTest {
    @Test
    public void tese01(){
        //实现excel写的操作
        //1. 设置写入文件夹地址和excel的文件名称
        String filename="F:\\write.xlsx";
        //2. 调用easyExcel中的写方法
        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(ExcelTest.getData());


    }
    @Test
    public void test02(){
//        String filename="F:\\maven_project\\data.xlsx";
        String filename="F:\\write.xlsx";
        EasyExcel.read(filename, SunkectData.class, new ExcleListenner()).sheet().doRead();


    }

    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            DemoData data=new DemoData();
            data.setSno(i);
            data.setSname("wys"+i);
            list.add(data);
        }
        return list;
    }

}
