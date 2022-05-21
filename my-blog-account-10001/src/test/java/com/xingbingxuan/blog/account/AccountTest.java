package com.xingbingxuan.blog.account;

import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.mapper.AccountMapper;
import com.xingbingxuan.blog.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author : xbx
 * @date : 2022/3/26 22:47
 */

@SpringBootTest
public class AccountTest {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void test003(){


        List<UserEntity> userEntities = accountMapper.selectAllUser();
        userEntities.forEach(System.out::println);
    }

    @Test
    public void test002(){
        String encode = bCryptPasswordEncoder.encode("121212");
        System.out.println(encode);
    }


    @Test
    public void test001(){
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUsername("test_1");
        userEntity1.setPassword("12121");
        userEntity1.setCreateTime(new Date());
        userEntity1.setNickname("12121");
        userEntity1.setHeader("211212121");

        Integer integer = accountMapper.insertAccount(userEntity1);
        System.out.println(integer);
    }
}
