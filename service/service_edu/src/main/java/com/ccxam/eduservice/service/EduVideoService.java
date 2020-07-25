package com.ccxam.eduservice.service;

import com.ccxam.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-18
 */
public interface EduVideoService extends IService<EduVideo> {

    void saveVideoChapter(EduVideo eduVideo);

    boolean deleteById(String id);
}
