package com.ccxam.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccxam.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccxam.eduservice.entity.FrontVo.CourseFrontVo;
import com.ccxam.eduservice.entity.FrontVo.CourseWebVo;
import com.ccxam.eduservice.entity.vo.CourseInfoVo;
import com.ccxam.eduservice.entity.vo.CoursePublishVo;
import com.ccxam.eduservice.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-18
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String courseId);

    Map<String,Object> getCourseListByPage(int current , int size , CourseQuery courseQuery);

    void deleteCourse(String id);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
