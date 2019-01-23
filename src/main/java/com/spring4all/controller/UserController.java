package com.spring4all.controller;

import com.spring4all.dao.UserDao;
import com.spring4all.domain.QQUser;
import com.spring4all.domain.Users;
import com.spring4all.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserDao userDao ;

    @GetMapping("/qqUser")
    public String user(@AuthenticationPrincipal UsernamePasswordAuthenticationToken userAuthentication, Model model){
        QQUser user = (QQUser) userAuthentication.getPrincipal();
        model.addAttribute("username", user.getNickname());
        model.addAttribute("avatar", user.getAvatar());
        return "user/user";
    }

    @GetMapping("/user")
    public String userDetail(@AuthenticationPrincipal UsernamePasswordAuthenticationToken userAuthentication, Model model){
        Users user = (Users) userAuthentication.getPrincipal();
        System.out.println(user.toString());
        return "login";
    }

    @PostMapping("/register")
    public String addUser(Users users, Model model){

        System.out.println(users.toString());
        if(users!=null&&!StringUtils.isEmpty(users.getPassword())){
            users.setPassword(MD5.addMd5(users.getPassword()));
        }
        int userId = userDao.addUser(users);
        //新增用户失败，则跳回首页，携带错误信息
        if(userId == 0){
            model.addAttribute("register", "用户新增失败");
            return "login";
        }else{
            model.addAttribute("username", users.getName());
            model.addAttribute("avatar","http://47.100.165.34:8081/FireImage/20181010/1539160818273.jpg" );
            return "user/user";
        }

    }

    @GetMapping("/wxUser")
    public String loginByWeiXin(HttpServletRequest request, Map<String, Object> map){
       // 获取code和state 2 个参数
       String code = request.getParameter("code");

       String state = request.getParameter("state");

      System.out.println("code -------"+code+", state ------- "+state);
        return "user/user";

    }
}
