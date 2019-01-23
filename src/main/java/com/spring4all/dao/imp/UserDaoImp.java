package com.spring4all.dao.imp;

import com.spring4all.dao.UserDao;
import com.spring4all.domain.Users;
import com.spring4all.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author by double.
 * @Date: 2018/10/9
 * @remarks:
 */
@Service
public class UserDaoImp implements UserDao {

    @Autowired
    UserMapper userMapper;

    @Override
    public Users findByUserName(String username) {
        return userMapper.findByUserName(username);
    }


    @Override
    public int addUser(Users users) {

        int count = userMapper.addUser(users);
        //当用户插入失败时返回0，添加成功时，返回用户新增编号
        return  count>0?users.getId():0;
    }
}
