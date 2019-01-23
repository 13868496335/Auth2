package com.spring4all;

import com.spring4all.config.ServerPortal;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @Auther:double
 * @Remarks:
 *   第三方登录：
 *    简单点理解：
 *   主要就是获取openId和accessToken的过程，因为有这俩就可以调 userInfoUrl 获取QQ用户信息
 *   用户点击了QQ登陆，就会调腾讯的url,进行登陆授权，授权成功就会跳转回调方法，这边自定义了一个qqFilter过滤器
 *   在认证账号密码登陆Filter之前。同时也自定义了一个认证处理器。
 *   请求回调方法的时候，在qqFilter过滤器拦截，通过request获取code,请求了一次获取accesstoken ,在请求一次获取openid,
 *   最后信息传入认证处理器。在通过openId和accessToken调 userInfoUrl 获取QQ用户信息
 *
 *
 *   记住我：
 *      通过web里面加.remember().tokenValiditySeconds(3600).tokenRepository(tokenRepository())
 *      来配置记住我这个功能，
 *      tokenValiditySeconds是设置Cookies存remember-token的有效时间，
 *      tokenRepository是配置数据源，存token到数据库里面，取token校验
 *  退出：
 *      登录和退出请求认证都需要post请求。安全退出都需要设置删除cookies和session失效。
 */
@MapperScan(value = "com/spring4all/mapper/*.xml")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean testServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServerPortal());
        //我们的URL
        registration.addUrlMappings("/portal");
        return registration;
    }
}

