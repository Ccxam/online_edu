package com.ccxam.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcleListenner extends AnalysisEventListener<SunkectData> {
    @Override
    public void invoke(SunkectData sunkectData, AnalysisContext analysisContext) {
        System.out.println("*****"+sunkectData);

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
