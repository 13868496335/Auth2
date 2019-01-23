package com.spring4all.dao;

import com.spring4all.domain.UserRoles;

import java.util.List;

/**
 * 
 * @Author by double.
 * @Date: 2018/10/9
 * @remarks:
 */
public interface SysPermissionDao {

    List<UserRoles> findByAdminUserId(Integer id);
}
