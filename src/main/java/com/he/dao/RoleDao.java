package com.he.dao;

import com.he.domain.Permission;
import com.he.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleDao {

    //根据用户id查询出搜有对应的角色
    @Select("select * from role where id in (select roleId from users_role where userId=#{userId} )")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many =@Many(select = "com.he.dao.PermissionDao.findPermissionByRoleId"))
    })
    List<Role> findRoleByUserId(Integer userId) throws Exception;

    @Select("select * from role")
    List<Role> findAll() throws Exception;

    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role) throws  Exception;

    @Select("select * from permission where id not in (select permissionId from role_permission where roleId=#{roleId})")
    List<Permission> findOtherPermissions(Integer roleId);

    @Insert("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId})")
    void addPermissionToRole(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    @Select("select * from role")
    Role findById(Integer roleId) throws Exception;
}
