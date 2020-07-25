package com.ccxam.eduservice.client.Impl;

import com.ccxam.commonutils.R;
import com.ccxam.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteAliVideo(String videoId) {
        return R.error().message("删除视频出错!");
    }

    @Override
    public R deleteBatchAliVideo(List<String> videoIdList) {
        return R.error().message("删除多个视频出错!");
    }
}
