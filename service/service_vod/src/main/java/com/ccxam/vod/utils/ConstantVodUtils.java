package com.ccxam.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils  implements InitializingBean {
    @Value("${aliyun.vod.file.keyid}")
    private String keyId;
    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;
    public static String ACESSKEY_ID;
    public static String ACESSKEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACESSKEY_ID=keyId;
        ACESSKEY_SECRET=keySecret;
    }
}
