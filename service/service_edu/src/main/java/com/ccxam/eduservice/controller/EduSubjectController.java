package com.ccxam.eduservice.controller;


import com.ccxam.commonutils.R;
import com.ccxam.eduservice.entity.EduSubject;
import com.ccxam.eduservice.entity.subject.OneSubject;
import com.ccxam.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-18
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
@Api(description = "课程管理接口")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("addSubject")
    @ApiOperation("添加课程")
    public R saveList(MultipartFile file){
        //添加课程分类
        subjectService.saveSubject(file,subjectService);
        //获取上传过来的文件，把文件内容读取出来
        return R.ok();
    }

    @GetMapping("getSubject")
    @ApiOperation("获取所有的课程")
    public R getList(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("data",list);
    }
}

