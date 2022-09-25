package com.xingbingxuan.blog.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.account.entity.RoleEntity;
import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.entity.UserRoleRelationEntity;
import com.xingbingxuan.blog.account.entity.bo.UserAndRoleBo;
import com.xingbingxuan.blog.account.mapper.RoleMapper;
import com.xingbingxuan.blog.account.mapper.UserMapper;
import com.xingbingxuan.blog.account.mapper.UserRoleRelationMapper;
import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.config.PublicConfigUtil;
import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.param.UserParam;
import com.xingbingxuan.blog.utils.*;
import com.xingbingxuan.blog.vo.RoleVo;
import com.xingbingxuan.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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


    @Override
    public PageInfo<UserEntity> queryAllUserPage(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<UserEntity> users = userMapper.selectAllUser();

        PageInfo<UserEntity> pageInfo = new PageInfo<>(users);


        return pageInfo;
    }

    @Override
    public UserEntity selectOneByUsernameAndSocialUid(UserEntity userEntity) {

        return null;
    }

    @Override
    public UserAndRoleBo selectOrSaveUserBySocialUidAndSocialType(UserParam userParam) {

        UserEntity parma = new UserEntity();

        BeanUtils.copyProperties(userParam,parma);
        UserAndRoleBo userAndRoleBo = userMapper.selectOneAnd(parma);

        if (userAndRoleBo != null){
            //更新登录时间
            UserEntity updateUser = new UserEntity();
            BeanUtils.copyProperties(userAndRoleBo,updateUser);
            updateUser.setIntegration(userAndRoleBo.getIntegration() +1);
            updateUser.setLastLoginTime(Calendar.getInstance().getTime());
            this.userMapper.updateUserById(updateUser);

        }else { //添加

            UserEntity insertParam = new UserEntity();
            BeanUtils.copyProperties(userParam,insertParam);
            userAndRoleBo = this.addDefaultUser(insertParam);

        }

        return userAndRoleBo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public UserAndRoleBo addDefaultUser(UserEntity userEntity) {

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

        UserAndRoleBo userAndRoleBo = new UserAndRoleBo();
        BeanUtils.copyProperties(insertUser,userAndRoleBo);
        userAndRoleBo.setRoleVos(Arrays.asList(roleVo));

        //密码不返回
        userAndRoleBo.setPassword(null);

        return userAndRoleBo;
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
            UserAndRoleBo userEntity1 = userMapper.selectOneAnd(userEntity);
            resultMap.put(entry.getKey().toString(),userEntity1.getHeader());
        }

        return resultMap;
    }

    @Override
    public UserAllInfoDto queryUserPasswordByUsername(String userName) {


        UserAndRoleBo selectOneAnd = userMapper.selectPasswordByUserName(userName);

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
        UserAndRoleBo user = userMapper.selectOneAnd(userEntity);

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
        UserAndRoleBo userEntity = null;

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
