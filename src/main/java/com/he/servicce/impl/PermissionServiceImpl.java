package com.he.servicce.impl;

import com.he.dao.PermissionDao;
import com.he.domain.Permission;
import com.he.servicce.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("permissionService")

public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    public List<Permission> findAll() throws Exception {
        return permissionDao.findAll();
    }

    @Override
    public void save(Permission permission) throws Exception {
        permissionDao.save(permission);
    }
}
