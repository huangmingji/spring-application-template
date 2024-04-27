package com.stargazer.springapplicationtemplate;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.stargazer.springapplicationtemplate.domain.User;
import com.stargazer.springapplicationtemplate.repositories.IUserRepository;
import com.stargazer.springapplicationtemplate.utils.SnowflakeIdGenerator;
import com.stargazer.springapplicationtemplate.utils.exceptions.VerifyPasswordException;

@SpringBootTest
public class UserMapperTests {

    private static final Logger log = LoggerFactory.getLogger(UserMapperTests.class);

    @Autowired
    private IUserRepository userRepository;

    @Test
    @Order(1)
    public void insertUsertest() {
        try {
            User user = new User();
            user.setId(SnowflakeIdGenerator.getInstance().nextId());
            user.setAccount("admin");
            user.setNickName("admin");
            user.setPassword("123456");
            user.setAvatar("11");
            user.setEmail("test@stargazer.tech");
            user.setPhoneNumber("123456");
            if (userRepository.insert(user) > 0) {
                log.info("insert success");
            } else {
                log.info("insert fail");
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        log.info("insert test");
    }

    @Test
    @Order(2)
    public void updateUsertest() {
        try {
            User user = userRepository.findByAccount("admin");
            user.setNickName("admin");
            user.setPassword("123456");
            user.setAvatar("11");
            user.setEmail("test@stargazer.tech");
            user.setPhoneNumber("123456");
            if (userRepository.update(user) > 0) {
                log.info("update success");
            } else {
                log.info("update fail");
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        log.info("update test");
    }

    @Test
    @Order(3)
    public void findUserTest() {
        User user = userRepository.findByAccount("admin");
        log.info("find test");
    }

    @Test
    @Order(4)
    public void verifyPasswordTest() throws VerifyPasswordException, NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            User user = userRepository.findByAccount("admin");
            user.VerifyPassword("12345674444");
        } catch(VerifyPasswordException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    @Order(5)
    public void deleteUsertest() {
        User user = userRepository.findByAccount("admin");
        if (userRepository.delete(user.getId()) > 0) {
            log.info("delete success");
        } else {
            log.info("delete fail");
        }
        log.info("delete test");
    }
}
