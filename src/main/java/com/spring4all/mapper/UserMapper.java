package com.spring4all.mapper;

import com.spring4all.domain.Users;
import org.apache.ibatis.annotations.*;

/**
 * 用户数据库交互类
 * @Author by double.
 * @Date: 2018/10/9
 * @remarks:
 */
@Mapper
public interface UserMapper {

    @Select("select * from users where account = #{username} ")
    public Users findByUserName(String username);

    @Insert("insert users (name,account,password)value(#{name},#{account},#{password})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public int  addUser(Users users);
}
