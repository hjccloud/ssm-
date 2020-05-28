package com.he.servicce.impl;

import com.he.dao.RoleDao;
import com.he.domain.Permission;
import com.he.domain.Role;
import com.he.servicce.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")

public class RoleServiceImpl implements RoleService {
        @Autowired
        RoleDao roleDao;

    @Override
    public void addPermissionToRole(Integer roleId, Integer[] permissionIds) {
        for(Integer permissionId:permissionIds){
            roleDao.addPermissionToRole(roleId,permissionId);
        }
    }

    @Override
    public Role findById(Integer roleId) throws Exception {
        return roleDao.findById(roleId);
    }

    @Override
    public List<Permission> findOtherPermissions(Integer roleId) {
        return roleDao.findOtherPermissions(roleId);
    }

    public List<Role> findAll() throws Exception {
        return roleDao.findAll();
    }

    @Override
    public void save(Role role) throws Exception {
        roleDao.save(role);
    }
}
