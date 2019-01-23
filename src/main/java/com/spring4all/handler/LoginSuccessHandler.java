package com.spring4all.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 设置自定义的登录成功处理器
 *
 * @Author by double.
 * @Date: 2018/10/9
 * @remarks:
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User userDetails = (User) authentication.getPrincipal();
        System.out.println("登录用户：username=" + userDetails.getUsername() + ", uri=" + request.getContextPath()+" 登陆成功！ ");
        super.onAuthenticationSuccess(request,response,authentication);
    }
}
