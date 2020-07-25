package com.ccxam.educenter.controller;


import com.ccxam.commonutils.JwtUtils;
import com.ccxam.commonutils.R;
import com.ccxam.educenter.entity.UcenterMember;
import com.ccxam.educenter.entity.vo.RegisterVo;
import com.ccxam.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-23
 */
@RestController
@RequestMapping("/educenter/ucentermember")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService service;
    //登录
    @PostMapping("login")
    public R login(@RequestBody UcenterMember ucenterMember) {

        //调用service方法实现
        //返回token值
        String token = service.login(ucenterMember);
        return R.ok().data("token",token);
    }


    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        service.register(registerVo);
        return R.ok();
    }

    //根据Token字符串来获取会员信息
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = service.getById(id);
        return R.ok().data("userInfo",member);
    }
    //https://open.weixin.qq.com/connect/qrconnect
}

