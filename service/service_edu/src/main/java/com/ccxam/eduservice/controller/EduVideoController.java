package com.ccxam.eduservice.controller;


import com.ccxam.commonutils.R;
import com.ccxam.eduservice.client.VodClient;
import com.ccxam.eduservice.entity.EduVideo;
import com.ccxam.eduservice.service.EduVideoService;
import com.ccxam.servicebase.exceptionhandler.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-18
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService service;
    @Autowired
    private VodClient vodClient;
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        service.save(eduVideo);
        return R.ok();
    }
    @GetMapping("/getVideoByID/{videoId}")
    public R addVideo(@PathVariable("videoId") String videoId){
        EduVideo eduVideo = service.getById(videoId);
        return R.ok().data("eduVideo",eduVideo);
    }

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        service.updateById(eduVideo);
        return R.ok();
    }

    //后面需要完善TODO
    @DeleteMapping("{videoId}")
    public R deleteVideoById(@PathVariable("videoId") String videoId){
        EduVideo eduVideo = service.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){

            //根据视频id，远程调用实现视频删除
            R r = vodClient.deleteAliVideo(videoSourceId);
            if (r.getCode()==20001){
                throw new MyException(20001,"删除视频失败熔断器");
            }
        }
        service.removeById(videoId);
//        service.deleteById(videoId);
        return R.ok();
    }

    public void show01(){

        Map<Integer,Integer> map = new HashMap<>();
        if (1==1&&2==2){

        }

    }


}

