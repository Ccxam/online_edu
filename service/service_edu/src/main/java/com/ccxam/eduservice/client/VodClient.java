package com.ccxam.eduservice.client;

import com.ccxam.commonutils.R;
import com.ccxam.eduservice.client.Impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    @DeleteMapping("/eduvod/video/deleteAliVideo/{videoId}")
    public R deleteAliVideo(@PathVariable("videoId") String videoId);


    //删除多个视频到阿里云TODO
    @DeleteMapping("/eduvod/video/deleteBatchAliVideo")
    public R deleteBatchAliVideo(@RequestParam("videoIdList") List<String> videoIdList);
}



