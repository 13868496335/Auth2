package com.spring4all.dao.imp;

import com.spring4all.dao.SysPermissionDao;
import com.spring4all.domain.UserRoles;
import com.spring4all.mapper.SysPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author by double.
 * @Date: 2018/10/9
 * @remarks:
 */
@Service
public class SysPermissionDaoImp implements SysPermissionDao {

    @Autowired
    SysPermissionMapper sysPermissionMapper;
    @Override
    public List<UserRoles> findByAdminUserId(Integer id) {
        return sysPermissionMapper.findByAdminUserId(id);
    }
}
