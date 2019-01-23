package com.spring4all.mapper;

import com.spring4all.domain.UserRoles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author by double.
 * @Date: 2018/10/9
 * @remarks:
 */
@Mapper
public interface SysPermissionMapper {

    @Select("select  *  from  user_roles a where a.user_id = #{id} ")
    public List<UserRoles> findByAdminUserId(Integer id);
}
