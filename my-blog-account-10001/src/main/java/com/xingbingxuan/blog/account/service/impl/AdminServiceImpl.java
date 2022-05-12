package com.xingbingxuan.blog.account.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xingbingxuan.blog.account.entity.AdminEntity;
import com.xingbingxuan.blog.account.mapper.AdminMapper;
import com.xingbingxuan.blog.account.service.AdminService;
import com.xingbingxuan.blog.utils.RedisUtil;
import com.xingbingxuan.blog.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author : xbx
 * @date : 2022/5/3 22:46
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addAdmin(AdminEntity adminEntity) {

        //判断用户名是否存在
        AdminEntity adminByName = adminMapper.selectAdminByName(adminEntity.getAdminName());
        if (adminByName != null){
            return 2;
        }
        //密码加密
        adminEntity.setAdminPassword(bCryptPasswordEncoder.encode(adminEntity.getAdminPassword()));
        //添加数据
        Integer admin = adminMapper.insertAdmin(adminEntity);

        return admin>0 ? admin : 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String login(AdminEntity adminEntity) {
        //根据用户名查询用户
        AdminEntity admin = adminMapper.selectAdminByName(adminEntity.getAdminName());

        if (admin == null){
            //用户不存在
            return "2";
        }
        boolean flag = bCryptPasswordEncoder.matches(adminEntity.getAdminPassword(), admin.getAdminPassword());
        if (flag){
            //登录成功，生成token存入redis
            String token = TokenUtil.generateToken(adminEntity.getAdminName());

            //JSON json = JSONUtil.parse(admin);
            boolean set = RedisUtil.set("admin:token:"+adminEntity.getAdminName(),token, 60 * 60 * 24);

            return set ? token : "3";
        }else {
            //密码错误
            return "0";
        }


    }
}
