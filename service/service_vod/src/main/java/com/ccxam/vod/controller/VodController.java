package com.ccxam.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.ccxam.commonutils.R;
import com.ccxam.servicebase.exceptionhandler.MyException;
import com.ccxam.vod.entity.InitObject;
import com.ccxam.vod.service.VodService;
import com.ccxam.vod.utils.ConstantVodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService service;

    //上传视频到阿里云
    @PostMapping("/uploadAliVideo")
    public R uploadAliVideo(MultipartFile file){
       String vidoId = service.uploadVideoAly(file);
        return R.ok().data("videoId",vidoId);
    }
    //删除视频到阿里云TODO
    @DeleteMapping("/deleteAliVideo/{videoId}")
    public R deleteAliVideo(@PathVariable("videoId") String videoId){
        service.deleteAlById(videoId);
        return R.ok();
    }


    //删除多个视频到阿里云TODO
    @DeleteMapping("/deleteBatchAliVideo")
    public R deleteBatchAliVideo(@RequestParam("videoIdList") List<String> videoIdList){
        service.deleteBatchAlById(videoIdList);
        return R.ok();
    }
    //根据视频Id获取视频的播放凭证
    @GetMapping("/getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable("videoId") String videoId){
        try {
            //创建初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACESSKEY_ID, ConstantVodUtils.ACESSKEY_SECRET);
            //创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request对象中设置视频的id
            request.setVideoId(videoId);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            System.out.println(playAuth);
//            return R.ok();
            return R.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new MyException(20001,"获取凭证失败");
        }
    }

}


