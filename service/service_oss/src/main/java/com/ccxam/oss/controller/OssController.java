package com.ccxam.oss.controller;


import com.ccxam.commonutils.R;
import com.ccxam.oss.service.Impl.OssServiceImpl;
import com.ccxam.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
@EnableSwagger2
public class OssController {
    @Autowired
    private OssService service;
    //上传头像的方法
    @PostMapping
    public R uploadFile(MultipartFile file){
        String url = service.uploadFileAvatar(file);
        System.out.println(url);
        return R.ok().data("url",url);
    }

}
