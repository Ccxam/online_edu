package com.ccxam.eduservice.service;

import com.ccxam.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccxam.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-18
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    boolean deleteById(String id);
}
