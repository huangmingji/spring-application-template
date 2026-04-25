package com.stargazer.springapplicationtemplate.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stargazer.springapplicationtemplate.domain.Role;

@Mapper
public interface IRoleRepository {

    Role findById(long id);

    Role findByName(@Param("name") String name);

    List<Role> findByUserId(@Param("userId") long userId);

    List<Role> findAll();

    int insert(Role role);

    int insertUserRole(@Param("userId") long userId, @Param("roleId") long roleId);

    int deleteById(long id);

    int deleteUserRole(@Param("userId") long userId, @Param("roleId") long roleId);

    boolean existsByName(@Param("name") String name);
}