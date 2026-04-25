package com.stargazer.springapplicationtemplate.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stargazer.springapplicationtemplate.domain.Permission;

@Mapper
public interface IPermissionRepository {

    Permission findById(long id);

    Permission findByName(@Param("name") String name);

    List<Permission> findByRoleId(@Param("roleId") long roleId);

    List<Permission> findByUserId(@Param("userId") long userId);

    List<Permission> findAll();

    int insert(Permission permission);

    int insertRolePermission(@Param("roleId") long roleId, @Param("permissionId") long permissionId);

    int deleteById(long id);

    int deleteRolePermission(@Param("roleId") long roleId, @Param("permissionId") long permissionId);

    boolean existsByName(@Param("name") String name);
}