package com.xingbingxuan.blog.account;

import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.entity.bo.UserAndRoleBo;
import com.xingbingxuan.blog.account.mapper.UserMapper;
import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.utils.CodeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

/**
 * @author : xbx
 * @date : 2022/3/26 22:47
 */

@SpringBootTest
public class AccountTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    AccountService accountService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void test003(){

        UserAndRoleBo gitee_xing = userMapper.selectPasswordByUserName("gitee_xing");
        System.out.println(gitee_xing);

    }

    @Test
    public void test002(){
        String code = CodeUtil.createCode();
        System.out.println(code);
    }


    @Test
    public void test001(){
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUsername("test_1");
        userEntity1.setPassword("12121");
        userEntity1.setCreateTime(new Date());
        userEntity1.setNickname("12121");
        userEntity1.setHeader("211212121");

        Integer integer = userMapper.insertAccount(userEntity1);
        System.out.println(integer);
    }
}
