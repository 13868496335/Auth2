package com.spring4all.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author by double.
 * @Date: 2018/10/9
 * @remarks:
 */
@Component
@ConfigurationProperties(value="qiyun.security.setting")
public class SecuritySettings {
  ;
    /**
     * 游客模式，被允许访问的URL
     */
    private  String  permitall;
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
    /**
     * 密码盐
     */
    public static  final  String  salt = "LLC";
    //记住我，token
    public static final String    DEFAULT_PARAMETER = "remember-me";

    public String getPermitall() {
        return permitall;
    }

    public void setPermitall(String permitall) {
        this.permitall = permitall;
    }
}
