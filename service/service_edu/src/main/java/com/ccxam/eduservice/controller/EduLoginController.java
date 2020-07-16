package com.ccxam.eduservice.controller;


import com.ccxam.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
@Api(description = "后端模拟登陆")
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    @ApiOperation("模拟登陆")
    @CrossOrigin
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }
    @ApiOperation("获得信息")
    @CrossOrigin
    @GetMapping("/info")
    public R info(){
//        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1674760321,2881373110&fm=26&gp=0.jpg");
    }



}
