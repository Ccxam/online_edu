package com.ccxam.educms.controller;


import com.ccxam.commonutils.R;
import com.ccxam.educms.entity.CrmBanner;
import com.ccxam.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-23
 */
@RestController
@RequestMapping("/educms/bannerFront")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService service;

    //查询所有的banner
    @ApiOperation("前台查询所有的Banner")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
//        List<CrmBanner> list = service.list();
        List<CrmBanner> list =service.selectAllBanner();
        return R.ok().data("list",list);
    }
}

