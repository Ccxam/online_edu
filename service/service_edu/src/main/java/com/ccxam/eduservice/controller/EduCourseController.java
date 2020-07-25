package com.ccxam.eduservice.controller;


import com.ccxam.commonutils.R;
import com.ccxam.eduservice.entity.vo.CourseInfoVo;
import com.ccxam.eduservice.entity.vo.CoursePublishVo;
import com.ccxam.eduservice.entity.vo.CourseQuery;
import com.ccxam.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-18
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService service;
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = service.saveCourseInfo(courseInfoVo);
        return  R.ok().data("courseId",id);
    }
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId")String courseId ){
        CourseInfoVo courseInfoVo = service.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        System.out.println(courseInfoVo);
        service.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    //得到最终发布的信息
    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable("courseId")String courseId){
        CoursePublishVo coursePublishVo = service.publishCourseInfo(courseId);

        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    //课程列表TODO
    @ApiOperation("课程的条件分页查询")
    @PostMapping("/getAllCourses/{current}/{size}")
    public R getCourseList(@PathVariable("current") @ApiParam(name ="current",value = "当前页",required = true) int current ,
                           @PathVariable("size") @ApiParam(name ="size",value = "没有显示的条目数",required = true) int size,
                           @RequestBody(required = false) CourseQuery courseQuery){
        Map<String,Object> courses = service.getCourseListByPage(current,size,courseQuery);
        return R.ok().data("total",courses.get("total")).data("courses",courses.get("courses"));
    }
    @DeleteMapping("/deleteCourseById/{id}")
    public R deleteCourseById(@PathVariable("id")String id){

        service.deleteCourse(id);
        return R.ok();
    }
}

