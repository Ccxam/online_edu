package com.ccxam.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideoAly(MultipartFile file);

    void deleteAlById(String videoId);

    void deleteBatchAlById(List videoIdList);
}
