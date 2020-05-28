package com.he.servicce;

import com.he.domain.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> findAll() throws Exception;

    void save(Permission permission)throws Exception;
}
