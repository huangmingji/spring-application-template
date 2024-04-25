package com.stargazer.springapplicationtemplate.repositories;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.stargazer.springapplicationtemplate.entities.Account;

/**
 * 账户信息数据访问接口
 *
 */
@Mapper
public interface IAccountRepository {
    
    /**
     * 根据用户名查找用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Select("SELECT * FROM account WHERE username = #{username}")
    Account findByUsername(@Param("username") String username);

    /**
     * 插入用户信息
     *
     * @param account 用户信息
     * @return 成功 1 失败 0
     */
    int insert(Account account);

    /**
     * 更新用户信息
     *
     * @param account 用户信息
     * @return 成功 1 失败 0
     */
    int update(Account account);
}
