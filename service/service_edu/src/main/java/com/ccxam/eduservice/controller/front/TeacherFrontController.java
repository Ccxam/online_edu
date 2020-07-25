package com.ccxam.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccxam.commonutils.R;
import com.ccxam.eduservice.entity.EduCourse;
import com.ccxam.eduservice.entity.EduTeacher;
import com.ccxam.eduservice.service.EduCourseService;
import com.ccxam.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController  {

    @Autowired
    private EduTeacherService service;
    @Autowired
    private EduCourseService courseService;
    @PostMapping("/getTeacherFrontList/{page}/{size}")
    public R getTeacherFrontList(@PathVariable("page") long page,@PathVariable("size") long size){
        Page<EduTeacher> pageTeacher = new Page<>(page,size);
         Map<String , Object> map = service.getTeacherFrontList(pageTeacher);
        //返回分页的所有数据
        return R.ok().data(map);
    }


    //2讲师详情的功能
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable("teacherId")String teacherId){
        EduTeacher teacher = service.getById(teacherId);
        //2 根据讲师id查询所教课程的信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> eduList = courseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",eduList);
    }


}
