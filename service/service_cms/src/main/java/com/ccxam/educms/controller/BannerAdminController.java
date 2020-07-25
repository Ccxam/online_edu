package com.ccxam.educms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccxam.commonutils.R;
import com.ccxam.educms.entity.CrmBanner;
import com.ccxam.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/educms/bannerAdmin")
@CrossOrigin
public class BannerAdminController {


    @Autowired
    private CrmBannerService service;

    //1. 分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable("page") long page,@PathVariable("limit") long limit){
        Page<CrmBanner> page1=new Page<>(page,limit);
//        QueryWrapper<CrmBanner>
        service.page(page1);
        List<CrmBanner> records = page1.getRecords();
        long total = page1.getTotal();
        return R.ok().data("total",total).data("list",records);
    }
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        service.save(crmBanner);
        return R.ok();
    }
    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = service.getById(id);
        return R.ok().data("item", banner);
    }
    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        service.updateById(banner);
        return R.ok();
    }
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        service.removeById(id);
        return R.ok();
    }
}

