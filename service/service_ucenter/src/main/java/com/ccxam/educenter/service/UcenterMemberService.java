package com.ccxam.educenter.service;

import com.ccxam.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccxam.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-23
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getByOpenId(String openid);
}
