package com.ccxam.msmservice.controller;

import com.ccxam.commonutils.R;
import com.ccxam.msmservice.service.MsmService;
import com.ccxam.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService service;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //发送短信
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable("phone")String phone){
        //1.从redis获取验证码,如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        System.out.println(code);
        if (!StringUtils.isEmpty(code)){
            System.out.println("redis中已经有短信了");
            return R.ok();
        }
        System.out.println("通过阿里云发送短信");
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean isSend = service.send(param,phone);
        if (isSend){
            //发送成功，把发送的验证码放到redis里面去
            //设置有效时间
            System.out.println(code);
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }

    }

}

