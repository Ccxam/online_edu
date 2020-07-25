package com.ccxam.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccxam.eduservice.entity.EduSubject;
import com.ccxam.eduservice.entity.excel.SubjectData;
import com.ccxam.eduservice.entity.subject.OneSubject;
import com.ccxam.eduservice.entity.subject.TwoSubject;
import com.ccxam.eduservice.listenner.SubjectExcelListener;
import com.ccxam.eduservice.mapper.EduSubjectMapper;
import com.ccxam.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-18
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1.查询所有一级分类 parent_id ==0
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapper);
        System.out.println(eduSubjects);
        //2. 查询所有二级分类 parent_id !=0
        QueryWrapper<EduSubject> wrapper2=new QueryWrapper<>();
        wrapper2.ne("parent_id","0");
        List<EduSubject> eduSubjects2 = baseMapper.selectList(wrapper2);
        System.out.println(eduSubjects2);
        //创建list集合，用于存储最终的封装数据
        List<OneSubject> list = new ArrayList<>();
        for (int i = 0; i < eduSubjects.size(); i++) {
            OneSubject oneSubject=new OneSubject();
            List<TwoSubject> list2=new ArrayList<>();
            BeanUtils.copyProperties(eduSubjects.get(i),oneSubject);
            for (int j = 0; j <eduSubjects2.size() ; j++) {
                TwoSubject twoSubject = new TwoSubject();
                if (eduSubjects2.get(j).getParentId().equals(eduSubjects.get(i).getId())){
                    BeanUtils.copyProperties(eduSubjects2.get(j),twoSubject);
                    list2.add(twoSubject);
                }
            }
            oneSubject.setChildren(list2);
            list.add(oneSubject);
        }
        System.out.println(list);
        return list;
    }
}
