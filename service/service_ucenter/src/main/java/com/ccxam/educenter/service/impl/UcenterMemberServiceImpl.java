package com.ccxam.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccxam.commonutils.JwtUtils;
import com.ccxam.commonutils.MD5;
import com.ccxam.educenter.entity.UcenterMember;
import com.ccxam.educenter.entity.vo.RegisterVo;
import com.ccxam.educenter.mapper.UcenterMemberMapper;
import com.ccxam.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccxam.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-23
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //登录的方法
    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        //手机号和密码的非空判断
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw  new MyException(20001,"登录失败");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查出来的对象是否为空
        if (mobileMember==null){
            throw  new MyException(20001,"登录失败");
        }
        //判断密码是否一样
        //因为存储到数据库中的密码是加密的所有我们输入的密码需要先加密后在和数据库中的密码进行比较


        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new MyException(20001,"登录失败");
        }
        //判读用户是否被禁用
        if (mobileMember.getIsDisabled()){
            throw new MyException(20001,"登录失败");
        }
        //登录成功
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }
    //注册功能
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        //手机号和密码的非空判断
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)||StringUtils.isEmpty(code)
        ||StringUtils.isEmpty(nickname)){
            throw  new MyException(20001,"注册失败");
        }
        //判断验证码是否正确
        //获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw  new MyException(20001,"注册失败");
        }
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer integer = baseMapper.selectCount(queryWrapper);
        if (integer>0){
            throw  new MyException(20001,"注册失败");
        }
        //数据添加到数据库中
        UcenterMember member = new UcenterMember();

        BeanUtils.copyProperties(registerVo,member);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        int insert = baseMapper.insert(member);
        if (insert==0){
            throw  new MyException(20001,"注册失败");
        }

    }

    @Override
    public UcenterMember getByOpenId(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }
}
