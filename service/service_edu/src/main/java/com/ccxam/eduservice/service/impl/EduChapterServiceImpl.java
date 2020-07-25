package com.ccxam.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccxam.eduservice.entity.EduChapter;
import com.ccxam.eduservice.entity.EduVideo;
import com.ccxam.eduservice.entity.chapter.ChapterVo;
import com.ccxam.eduservice.entity.chapter.VideoVo;
import com.ccxam.eduservice.mapper.EduChapterMapper;
import com.ccxam.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccxam.eduservice.service.EduVideoService;
import com.ccxam.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-18
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService service;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);


        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> videoLists = service.list(wrapperVideo);

        List<ChapterVo> finallList = new ArrayList<>();



        for (int i = 0; i <eduChapters.size() ; i++) {
            List<VideoVo> lists = new ArrayList<>();
            EduChapter eduChapter = eduChapters.get(i);
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finallList.add(chapterVo);
            for (int j = 0; j <videoLists.size(); j++) {
                EduVideo eduVideo=videoLists.get(j);
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    lists.add(videoVo);
                }
            }
            finallList.get(i).setChildren(lists);
        }
        return finallList;
    }
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper =new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = service.count(wrapper);
        if (count>0){
            throw new MyException(20001,"该章节还有小节，不能删除！");
        }else {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }
    @Override
    public boolean deleteById(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        int delete = baseMapper.delete(wrapper);
        return delete>0;
    }
}
