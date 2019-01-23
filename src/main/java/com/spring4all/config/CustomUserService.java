package com.spring4all.config;

import com.spring4all.constant.SecuritySettings;
import com.spring4all.dao.SysPermissionDao;
import com.spring4all.dao.UserDao;
import com.spring4all.domain.UserRoles;
import com.spring4all.domain.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 自定义UserDetailsService,将用户权限交给springsecurity进行管控
 *   简单的说：
 *      UsernamePasswordAuthenticationFilter认证通过了之后，
 *      会调用UserDetailsService来获取用户信息,这边通过自定义UserDetailsService，
 *      来通过数据库查询用户信息。实现与数据库之间的交互
 *
 * UsernamePasswordAuthenticationManager 里面的 doAuthentication 认证方法会调用
 * 类中的List providers集合中的各个AuthenticationProvider接口
 * 实现类中的authenticate(Authentication authentication)方法进行验证，
 * 由此可见，真正的验证逻辑是由各个各个AuthenticationProvider接口实现类来完成的,
 * DaoAuthenticationProvider类是默认情况下注入的一个AuthenticationProvider接口实现类,
 *
 * 4.AuthenticationProvider接口通过UserDetailsService来获取用户信息
 *
 *
 * //注入userDetailsService的实现类
 auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
 *
 * @Author by double.
 * @Date: 2018/10/9
 * @remarks:
 */

@Slf4j
public class CustomUserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SysPermissionDao sysPermissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //1.根据用户名,获取用户信息
        Users user = userDao.findByUserName(username);
        if (user != null) {
            //2.根据用户编号，查询用户对应的角色信息
            List<UserRoles> permissions = sysPermissionDao.findByAdminUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (UserRoles permission : permissions) {
                if (permission != null && permission.getRoleName()!=null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getRoleName());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }

            return new User(user.getName(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }
/*
   @Test
    public void test() {
        String pwd = "12345";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encodedPassword = passwordEncoder.encode(pwd);
        log.info("【加密后的密码为：】" + encodedPassword);
    }
    */
}