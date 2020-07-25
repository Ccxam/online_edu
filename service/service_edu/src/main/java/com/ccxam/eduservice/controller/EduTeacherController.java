package com.ccxam.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccxam.commonutils.R;
import com.ccxam.commonutils.TeacherQuery;
import com.ccxam.eduservice.entity.EduTeacher;
import com.ccxam.eduservice.service.EduTeacherService;
import com.ccxam.servicebase.exceptionhandler.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@Api(description="讲师接口")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService service;
    //1.查询讲师表中的所有数据
    @ApiOperation("查询所有的讲师")
    @CrossOrigin
    @GetMapping("/findAll")
    public R getList(){
        List<EduTeacher> list = service.list(null);
        return R.ok().data("items",list);
    }
    @ApiOperation("逻辑删除讲师")
    @CrossOrigin
    @DeleteMapping("/deleteById/{id}")
    public R deleteTeacherById(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable("id") String id){
        boolean flag = service.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("/pageTeacher/{current}/{size}")
    @ApiOperation("教师分页查询")
    public R pageListTeacher(@ApiParam(name="current",value = "当前页",required = true) @PathVariable("current") int current,
                             @ApiParam(name="size",value = "每页的条目数",required = true) @PathVariable("size")int size){
        Page<EduTeacher> pageTeacher = new Page<>(1,3);
//        try {
//            int i=4/0;
//        } catch (Exception e) {
//            throw new MyException(20001,"执行了自定义异常处理");
//        }
        service.page(pageTeacher,null);
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",pageTeacher.getTotal()).data("list",records);
    }
//    @PostMapping("/pageTeacherCondition/{current}/{size}/{teacherQuery}")
    @PostMapping("/pageTeacherCondition/{current}/{size}")
    @ApiOperation("教师条件查询")
    @CrossOrigin
    public R pageListTeacherCondition(@ApiParam(name="current",value = "当前页",required = true) @PathVariable("current") int current,
             @ApiParam(name="size",value = "每页的条目数",required = true) @PathVariable("size")int size,
             @RequestBody(required = false) TeacherQuery teacherQuery){
//        @ApiParam(name="teacherQuery",value = "教师查询对象",required = true) @PathVariable("teacherQuery")
        Page<EduTeacher> pageTeacher = new Page<>(current,size);
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        service.page(pageTeacher,wrapper);
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",pageTeacher.getTotal()).data("list",records);
    }
    @PostMapping("addTeacher")
    @ApiOperation("添加讲师的方法")
    public R addTeacher(@RequestBody  EduTeacher eduteacher){
        boolean flag = service.save(eduteacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @PostMapping("/queryTeacherById/{id}")
    @ApiOperation("通过ID查询讲师")
    @CrossOrigin
    public R queryTeacherById(@PathVariable("id")@ApiParam(name = "id",value = "讲师ID",required = true) String id){
        EduTeacher teacher = service.getById(id);
        return R.ok().data("list",teacher);
    }

    @PostMapping("/updateTeacherById}")
    @ApiOperation("通过ID修改讲师")
    public R updateTeacherById(@RequestBody EduTeacher teacher){
        boolean flag = service.updateById(teacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
//    LTAI4GFN6FZixGK5xzXu6Ygf
//    0O9ZCSow0JtXrcL2NHcFnhvg8o7Ilg
}

