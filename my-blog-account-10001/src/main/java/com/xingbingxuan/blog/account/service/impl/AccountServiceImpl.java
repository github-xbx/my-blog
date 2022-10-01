package com.xingbingxuan.blog.account.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.account.entity.RoleEntity;
import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.entity.UserRoleRelationEntity;
import com.xingbingxuan.blog.account.entity.bo.UserAndRoleRelation;
import com.xingbingxuan.blog.account.feign.AccountAuthorizeServiceFeign;
import com.xingbingxuan.blog.account.mapper.RoleMapper;
import com.xingbingxuan.blog.account.mapper.UserMapper;
import com.xingbingxuan.blog.account.mapper.UserRoleRelationMapper;
import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.config.PublicConfigUtil;
import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.param.UserParam;
import com.xingbingxuan.blog.token.AccessToken;
import com.xingbingxuan.blog.utils.*;
import com.xingbingxuan.blog.vo.RoleVo;
import com.xingbingxuan.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author : xbx
 * @date : 2022/3/25 20:15
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;
    @Autowired
    private AccountAuthorizeServiceFeign accountAuthorizeServiceFeign;


    @Override
    public PageInfo<UserEntity> queryAllUserPage(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<UserEntity> users = userMapper.selectAllUser();

        PageInfo<UserEntity> pageInfo = new PageInfo<>(users);


        return pageInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public JSON userLogin(Map<String,String> userParam) {

        String username = userParam.get("username");

        UserAndRoleRelation usernameAndPassword = null;
        JSONObject result = new JSONObject();

        if (username.matches(PublicConfigUtil.REGULAR_EMAIL)){
            //邮箱
            UserEntity user = new UserEntity();
            user.setEmail(username);
            usernameAndPassword = userMapper.selectPasswordByUserNameOrEmail(user);


        }else {
            //用户名
            UserEntity user = new UserEntity();
            user.setUsername(username);
            usernameAndPassword = userMapper.selectPasswordByUserNameOrEmail(user);

        }

        if (usernameAndPassword == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        //验证密码
        String passwordStr = userParam.get("password");
        if (!bCryptPasswordEncoder.matches(passwordStr,usernameAndPassword.getPassword())){
            throw new BadCredentialsException("密码错误");

        }
        //更新登录时间
        UserEntity updateUser = new UserEntity();
        BeanUtils.copyProperties(usernameAndPassword,updateUser);
        updateUser.setIntegration(usernameAndPassword.getIntegration() +1);
        updateUser.setLastLoginTime(Calendar.getInstance().getTime());
        this.userMapper.updateUserById(updateUser);

        //获取token
        UserAllInfoDto userAllInfoDto = new UserAllInfoDto();

        BeanUtils.copyProperties(usernameAndPassword,userAllInfoDto);

        AccessToken accessToken = accountAuthorizeServiceFeign.loginToken(userAllInfoDto);

        //封装返回信息
        result.putOnce("token",accessToken.getToken());
        result.putOnce("user",userAllInfoDto);



        return result;
    }








    @Override
    @Transactional
    public UserAndRoleRelation selectOrSaveUserBySocialUidAndSocialType(UserParam userParam) {

        UserEntity parma = new UserEntity();

        BeanUtils.copyProperties(userParam,parma);
        UserAndRoleRelation userAndRoleRelation = userMapper.selectOneAnd(parma);

        if (userAndRoleRelation != null){
            //更新登录时间
            UserEntity updateUser = new UserEntity();
            BeanUtils.copyProperties(userAndRoleRelation,updateUser);
            updateUser.setIntegration(userAndRoleRelation.getIntegration() +1);
            updateUser.setLastLoginTime(Calendar.getInstance().getTime());
            this.userMapper.updateUserById(updateUser);

        }else { //添加

            UserEntity insertParam = new UserEntity();
            BeanUtils.copyProperties(userParam,insertParam);
            userAndRoleRelation = this.addDefaultUser(insertParam);

        }

        return userAndRoleRelation;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public UserAndRoleRelation addDefaultUser(UserEntity userEntity) {

        UserEntity insertUser = new UserEntity();
        //默认头像图片
        insertUser.setHeader(PublicConfigUtil.USER_DEFAULT_HEADER_IMAGE_URL);
        insertUser.setCreateTime(Calendar.getInstance().getTime());
        insertUser.setLastLoginTime(Calendar.getInstance().getTime());
        //默认用户名 第三方类型+ _随机字符串
        insertUser.setUsername(userEntity.getSocialType()+"_"+CodeUtil.createCode());
        //默认密码
        insertUser.setPassword(bCryptPasswordEncoder.encode(PublicConfigUtil.USER_DEFAULT_PASSWORD));
        insertUser.setSocialUid(userEntity.getSocialUid());
        insertUser.setSocialType( userEntity.getSocialType());
        this.userMapper.insertAccount(insertUser);

        //添加用户的默认角色（权限）
        UserRoleRelationEntity userRoleRelationEntity = new UserRoleRelationEntity();
        userRoleRelationEntity.setUserId(insertUser.getId());
        userRoleRelationEntity.setRoleId(1);

        //添加用户的角色信息
        userRoleRelationMapper.insertUserRoleRelation(userRoleRelationEntity);

        //获取默认角色的信息
        RoleEntity roleEntity = roleMapper.selectOneById(1);
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(roleEntity,roleVo);

        UserAndRoleRelation userAndRoleRelation = new UserAndRoleRelation();
        BeanUtils.copyProperties(insertUser, userAndRoleRelation);
        userAndRoleRelation.setRoleVos(Arrays.asList(roleVo));

        //密码不返回
        userAndRoleRelation.setPassword(null);

        return userAndRoleRelation;
    }



    @Override
    public Integer queryAccountCount() {
        return userMapper.selectAccountCount();
    }

    @Override
    public List queryAccountCountByThisWeek() {

        List<UserEntity> users = userMapper.selectByThisWeek();

        List<String> time = DateTool.getThisWeekTime();

        List<Map<String, Object>> lists = new ArrayList<>();

        for (String s : time) {
            long count = users.stream().filter(user -> {
                String createTime = DateTool.DateToString("yyyy-MM-dd", user.getCreateTime());
                return createTime.equals(s);
            }).count();

            lists.add(new HashMap<String, Object>() {{
                put("date", s);
                put("count", count);
            }});
        }


        return lists;
    }

    @Override
    public Map<String, Object> queryUserHeaderByIds(Map<Integer, Integer> blogIdAndUserId) {

        HashMap<String, Object> resultMap = new HashMap<>();

        if (blogIdAndUserId == null) {
            return resultMap;
        }
        for (Map.Entry<Integer, Integer> entry : blogIdAndUserId.entrySet()) {
            Integer userid = entry.getValue();
            UserEntity userEntity = new UserEntity();
            userEntity.setId(Long.valueOf(userid));
            UserAndRoleRelation userEntity1 = userMapper.selectOneAnd(userEntity);
            resultMap.put(entry.getKey().toString(),userEntity1.getHeader());
        }

        return resultMap;
    }

    @Override
    public UserAllInfoDto queryUserPasswordByUsername(String userName) {


        UserAndRoleRelation selectOneAnd = userMapper.selectPasswordByUserName(userName);

        UserAllInfoDto result = new UserAllInfoDto();

        BeanUtils.copyProperties(selectOneAnd,result);

        return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = {RuntimeException.class,Exception.class})
    public UserVo queryUserInfoByToken(String token) {


        UserVo userVo = new UserVo();

        String userId = TokenUtil.getObjectByToken(Base64.getDecoder().decode(token));

        UserEntity userEntity = new UserEntity();
        userEntity.setId(Long.valueOf(userId));
        UserAndRoleRelation user = userMapper.selectOneAnd(userEntity);

        BeanUtils.copyProperties(user,userVo);


        userEntity.setLastLoginTime(new Date());
        userEntity.setIntegration(user.getIntegration()+1);
        userMapper.updateUserById(userEntity);

        return userVo;
    }

    @Override
    public Boolean logout(Long userId,String token) {

        Long loginUserId = Long.valueOf(TokenUtil.getObjectByToken(Base64.getDecoder().decode(token)));

        if (loginUserId.equals(userId)){
            byte[] key = SerializeUtil.serializeKey(TokenUtil.getTokenKey(userId));

            RedisUtil.del(key);
            return true;
        }else {
            return false;
        }


    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVo isAccount(Map param) {

        //封装用户信息
        UserAndRoleRelation userEntity = null;

        UserVo userVo = new UserVo();


        UserEntity selectUser = new UserEntity();
        selectUser.setUsername((String) param.get("username"));
        userEntity = this.userMapper.selectOneAnd(selectUser);

        if (userEntity != null){
            userEntity.setIntegration(userEntity.getIntegration() +1);
            userEntity.setLastLoginTime(Calendar.getInstance().getTime());
            UserEntity userEntity1 = new UserEntity();
            BeanUtils.copyProperties(userEntity,userEntity1);
            this.userMapper.updateUserById(userEntity1);
            log.info("user -> {}",userEntity);
            BeanUtils.copyProperties(userEntity,userVo);
        }else { //添加
            UserEntity userEntity1 = new UserEntity();
            userEntity1.setHeader((String) param.get("header"));
            userEntity1.setCreateTime(Calendar.getInstance().getTime());
            userEntity1.setLastLoginTime(Calendar.getInstance().getTime());
            userEntity1.setUsername((String) param.get("username"));
            //默认密码
            userEntity1.setPassword(new BCryptPasswordEncoder().encode("00000000"));
            userEntity1.setSocialUid((String) param.get("socialUid"));
            userEntity1.setSocialType((String) param.get("socialType"));
            Integer integer = this.userMapper.insertAccount(userEntity1);
            log.info("user -> {}",userEntity1);
            BeanUtils.copyProperties(userEntity1,userVo);

        }






        return userVo;
    }


}
