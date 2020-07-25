package com.ccxam.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccxam.eduservice.entity.EduCourse;
import com.ccxam.eduservice.entity.EduCourseDescription;
import com.ccxam.eduservice.entity.FrontVo.CourseFrontVo;
import com.ccxam.eduservice.entity.FrontVo.CourseWebVo;
import com.ccxam.eduservice.entity.vo.CourseInfoVo;
import com.ccxam.eduservice.entity.vo.CoursePublishVo;
import com.ccxam.eduservice.entity.vo.CourseQuery;
import com.ccxam.eduservice.mapper.EduCourseMapper;
import com.ccxam.eduservice.service.EduChapterService;
import com.ccxam.eduservice.service.EduCourseDescriptionService;
import com.ccxam.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccxam.eduservice.service.EduVideoService;
import com.ccxam.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService service;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1. 向课程表里面添加课程的基本信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert<=0){
            throw new  MyException(20001,"添加课程信息失败");
        }
        // 获取添加之后的课程ID
        String cid = eduCourse.getId();
        //2. 向课程简介表中添加课程的简介
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        boolean save = service.save(eduCourseDescription);
        if (!save){
            throw new MyException(20001,"添加课程信息失败");
        }
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1. 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2.查询描述表
        QueryWrapper<EduCourseDescription> wrapper = new QueryWrapper<>();
        wrapper.eq("id",courseId);
        EduCourseDescription eduDescriotion = service.getById(courseId);
        courseInfoVo.setDescription(eduDescriotion.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update==0){
            throw new MyException(20001,"修改课程信息失败");
        }
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean flag = service.updateById(eduCourseDescription);
        if (!flag){
            throw new MyException(20001,"修改失败");
        }
    }
    @Override
    public CoursePublishVo publishCourseInfo(String courseId) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    @Override
    public Map<String,Object> getCourseListByPage(int current , int size, CourseQuery courseQuery) {
        Page<EduCourse> page = new Page<>(current,size);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();

        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        baseMapper.selectPage(page,wrapper);
        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("courses",records);
        return map;
    }

    @Override
    public void deleteCourse(String id) {
        boolean video_flag = eduVideoService.deleteById(id);
        boolean chapter_flag = eduChapterService.deleteById(id);
        boolean des_flag = service.removeById(id);
        int i = baseMapper.deleteById(id);
        if (i==0){
            throw new MyException(20001,"删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不空则拼接
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count ",courseFrontVo.getBuyCountSort());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create ",courseFrontVo.getGmtCreateSort());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            wrapper.orderByDesc("price ",courseFrontVo.getPriceSort());
        }
        baseMapper.selectPage(pageParam,wrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;

    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {





        return baseMapper.getBaseCourseInfo(courseId);
    }


}
