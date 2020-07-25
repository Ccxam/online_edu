package com.ccxam.educenter.controller;


import com.ccxam.commonutils.JwtUtils;
import com.ccxam.educenter.entity.UcenterMember;
import com.ccxam.educenter.service.UcenterMemberService;
import com.ccxam.educenter.utils.ConstantWxUtils;
import com.ccxam.educenter.utils.HttpClientUtils;
import com.ccxam.servicebase.exceptionhandler.MyException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService service;
    //1.生成微信扫描的二维码
    @GetMapping("login")
    public String getWxCode(){
        //微信平台开发授权baseUrl  %s相当于？代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect"+
                        "?appid=%s" +
                        "&redirect_uri=%s" +
                        "&response_type=code" +
                        "&scope=snsapi_login" +
                        "&state=%s" +
                        "#wechat_redirect";
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        System.out.println(redirectUrl);
        try {
            //对redirect_url进行URLEncoder编码(就是讲里面的特殊符号进行一下处理)
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
            System.out.println(redirectUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置%s的值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "ccxam"
        );
        //请求微信地址
        return "redirect:"+url;
    }
    //2.获取扫描机器人的信息，添加数据
    @GetMapping("callback")
    public String callback(String code,String state){
        try {
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token"+
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数: id 秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            //请求这个拼接好的地址，得到返回两个值 access_token 和 openid
            //httpclient发送请求
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //把accessTokenInfo转成map
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");
            //把扫码人信息添加到数据库
            UcenterMember member = service.getByOpenId(openid);
            if (member==null){
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo"+
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap mapUserInfo = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) mapUserInfo.get("nickname");
//            String  sex = (String) mapUserInfo.get("sex");
                String  headimgurl = (String) mapUserInfo.get("headimgurl");
                //如果为空则需要添加注册
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                service.save(member);
            }
            System.out.println(member);
            //使用jwt根据member对象生成token字符串

            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            return "redirect:http://localhost:3000?token="+jwtToken;
            //
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001,"登录失败");
        }
    }
}
