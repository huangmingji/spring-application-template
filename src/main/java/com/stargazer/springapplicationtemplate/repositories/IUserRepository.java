package com.stargazer.springapplicationtemplate.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stargazer.springapplicationtemplate.domain.User;

/**
 * 用户数据访问接口
 */
@Mapper
public interface IUserRepository {
    
    /**
     * 根据账号查询用户
     */
    User findByAccount(String account);

    /**
     * 插入用户
     */
    int insert(User user);

    /**
     * 更新用户
     */
    int update(User user);

    /**
     * 根据id查询用户
     */
    User findById(long id);

    /**
     * 删除用户
     */
    int delete(long id);

    List<User> findList();
}
