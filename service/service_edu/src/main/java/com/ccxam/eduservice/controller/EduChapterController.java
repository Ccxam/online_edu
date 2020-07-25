package com.ccxam.eduservice.controller;


import com.ccxam.commonutils.R;
import com.ccxam.eduservice.entity.EduChapter;
import com.ccxam.eduservice.entity.chapter.ChapterVo;
import com.ccxam.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-18
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService service;

    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId")String courseId){
        List<ChapterVo> chapterVos = service.getChapterVideoByCourseId(courseId);
        return  R.ok().data("chapterLists",chapterVos);
    }
    //添加章节
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        service.save(eduChapter);
        return  R.ok();
    }
    //根据章节ID进行查询
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable("chapterId")String chapterId){
        EduChapter ed = service.getById(chapterId);
        return  R.ok().data("chapter",ed);
    }
    //根据章节ID进行修改
    @PostMapping("/updateChapterInfo")
    public R updateChapterInfo(@RequestBody EduChapter eduChapter){
        boolean flag = service.updateById(eduChapter);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //删除章节
    @DeleteMapping("deleteChapterById/{chapterId}")
    public R deleteChapterById(@PathVariable("chapterId")String chapterId){
        boolean flag = service.deleteChapter(chapterId);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }
}

