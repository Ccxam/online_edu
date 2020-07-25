package com.ccxam.eduservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccxam.commonutils.R;
import com.ccxam.eduservice.entity.EduCourse;
import com.ccxam.eduservice.entity.FrontVo.CourseFrontVo;
import com.ccxam.eduservice.entity.FrontVo.CourseWebVo;
import com.ccxam.eduservice.entity.chapter.ChapterVo;
import com.ccxam.eduservice.service.EduChapterService;
import com.ccxam.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/coursefromt/")
public class CourseFrontController {
    @Autowired
    private EduCourseService service;
    @Autowired
    private EduChapterService chapterService;
    //1.条件查询带分页查询功能
    @PostMapping("/getFrontCourseList/{page}/{size}")
    public R getFrontCourseList(@PathVariable("page") long page,
                                @PathVariable("size") long size,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page,size);
        Map<String ,Object> map = service.getCourseFrontList(pageCourse,courseFrontVo);
        return R.ok().data(map);
    }
    //2.课程详情查询
    @GetMapping("/getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable("courseId") String courseId){
        //根据课程id编写sql语句查询课程信息
        CourseWebVo courseWebVo = service.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideo = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideo",chapterVideo);
    }

}
