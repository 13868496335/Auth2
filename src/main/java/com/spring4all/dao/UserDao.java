package com.spring4all.dao;

import com.spring4all.domain.Users;

/**
 * @Author by double.
 * @Date: 2018/10/9
 * @remarks:
 */
public interface UserDao {
    /**
     * 根据用户名称，查询用户信息
     * @param username
     * @return
     */
    public Users findByUserName(String username);


    /**
     * 新增用户
     * @param users
     * @return
     */
    public int  addUser(Users users);
}
