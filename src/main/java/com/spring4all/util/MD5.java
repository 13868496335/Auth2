package com.spring4all.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author by double.
 * @Date: 2018/10/10
 * @remarks:
 */
public class MD5 {

    private  static  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static   String  addMd5(String pwd){
        return  passwordEncoder.encode(pwd);
    }
}
