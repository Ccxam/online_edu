package com.ccxam.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccxam.commonutils.R;
import com.ccxam.eduservice.entity.EduCourse;
import com.ccxam.eduservice.entity.EduTeacher;
import com.ccxam.eduservice.service.EduCourseService;
import com.ccxam.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/eduservice/indexFront")
@CrossOrigin
public class IndexFrontController {


    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询前8条热门课程
    @Cacheable(value = "courseAndTeacher",key = "'selectCourseTeacher'")
    @GetMapping("/indexCourse")
    public R index(){
        QueryWrapper<EduCourse> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.orderByDesc("id");
        queryWrapper1.last("limit 8");
        List<EduCourse> eduList = eduCourseService.list(queryWrapper1);
        QueryWrapper<EduTeacher> queryWrapper2=new QueryWrapper<>();
        queryWrapper2.orderByDesc("id");
        queryWrapper2.last("limit 4");
        List<EduTeacher> eduTeacher = eduTeacherService.list(queryWrapper2);
        return R.ok().data("eduLists",eduList).data("eduTeachers",eduTeacher);
    }


}
