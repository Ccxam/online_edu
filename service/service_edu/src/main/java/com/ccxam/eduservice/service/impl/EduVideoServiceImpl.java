package com.ccxam.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccxam.eduservice.client.VodClient;
import com.ccxam.eduservice.entity.EduVideo;
import com.ccxam.eduservice.mapper.EduVideoMapper;
import com.ccxam.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-18
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;
    @Override
    public void saveVideoChapter(EduVideo eduVideo) {


    }

    @Override
    public boolean deleteById(String id) {
        QueryWrapper<EduVideo> wrappers=new QueryWrapper<>();
        wrappers.eq("course_id",id);
        wrappers.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrappers);
        List<String> videoList=new ArrayList<>();
        for (int i = 0; i < eduVideos.size(); i++) {
            String videoSourceId=eduVideos.get(i).getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)){
                videoList.add(videoSourceId);
            }
        }
        if (videoList.size()>0){
            vodClient.deleteBatchAliVideo(videoList);
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        int delete = baseMapper.delete(wrapper);
        return delete>0;




    }
}
