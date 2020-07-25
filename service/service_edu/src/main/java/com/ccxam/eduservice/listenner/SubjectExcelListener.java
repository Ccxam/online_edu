package com.ccxam.eduservice.listenner;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccxam.eduservice.entity.EduSubject;
import com.ccxam.eduservice.entity.excel.SubjectData;
import com.ccxam.eduservice.service.EduSubjectService;
import com.ccxam.servicebase.exceptionhandler.MyException;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //因为SubjectExcelListener不能交给Spring进行管理，所以不能自动注入
    public EduSubjectService service;

    public SubjectExcelListener(EduSubjectService service) {
        this.service = service;
    }
    public SubjectExcelListener() {
    }
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData==null){
            throw new MyException(20001,"文件数据为空");
        }
        //一行一行进行读取，每次读取两个值，第一个值为一级分类，第二个值为二级分类
        //判断一级分类是否重复
        EduSubject eduSubject = this.existOneSubject(service, subjectData.getOneSubjectName());

        if (eduSubject==null){
            eduSubject = new EduSubject();
            eduSubject.setTitle(subjectData.getOneSubjectName());
            eduSubject.setParentId("0");
            service.save(eduSubject);
        }
        String pid=eduSubject.getId();
        EduSubject edu2Subject = this.existTwoSubject(service, subjectData.getTwoSubjectName(),pid);
        if (edu2Subject==null){
            edu2Subject = new EduSubject();
            edu2Subject.setTitle(subjectData.getTwoSubjectName());
            edu2Subject.setParentId(pid);
            service.save(edu2Subject);
        }

    }

    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println(headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
