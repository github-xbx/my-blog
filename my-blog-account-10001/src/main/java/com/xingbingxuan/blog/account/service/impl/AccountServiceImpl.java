package com.xingbingxuan.blog.account.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.mapper.AccountMapper;
import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.utils.*;
import com.xingbingxuan.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
    private AccountMapper accountMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public PageInfo<UserEntity> queryAllUserPage(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<UserEntity> users = accountMapper.selectAllUser();

        PageInfo<UserEntity> pageInfo = new PageInfo<>(users);


        return pageInfo;
    }

    @Override
    public UserEntity selectOneByUsernameAndSocialUid(UserEntity userEntity) {

        return accountMapper.selectOneAnd(userEntity);
    }

    @Override
    public Integer addAccount(UserEntity userEntity) {
        return 1;
    }

    @Override
    public UserEntity giteeLogin(String token) {

        //??????accessToken??????gitee?????????????????????
        HttpResponse accessTokenResponse = HttpRequest.get("https://gitee.com/api/v5/user")
                .form("access_token", token)
                .execute();
        //?????????????????????????????????
        UserEntity user = null;

        if (accessTokenResponse.getStatus() == 200) {
            JSONObject userInfoJson = JSONUtil.parseObj(accessTokenResponse.body());
            log.info("gitee ????????????????????? -> {}",userInfoJson.toString());
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("gitee_" + userInfoJson.get("name").toString());
            userEntity.setSocialType("gitee");
            userEntity.setSocialUid(userInfoJson.get("id").toString());

            //???????????????????????????????????????????????????
            user = accountMapper.selectOneAnd(userEntity);
            if (user == null) {
                //????????????????????????
                userEntity.setCreateTime(Calendar.getInstance().getTime());
                userEntity.setPassword(bCryptPasswordEncoder.encode("00000000"));
                userEntity.setHeader(userInfoJson.get("avatar_url").toString());
                Integer integer = accountMapper.insertAccount(userEntity);
                if (integer > 0) {
                    //???????????????????????????
                    userEntity.setPassword(null);
                    user = userEntity;
                }
            } else {
                //??????
            }
        }
        return user;
    }

    @Override
    public Integer queryAccountCount() {
        return accountMapper.selectAccountCount();
    }

    @Override
    public List queryAccountCountByThisWeek() {

        List<UserEntity> users = accountMapper.selectByThisWeek();

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
            UserEntity userEntity1 = accountMapper.selectOneAnd(userEntity);
            resultMap.put(entry.getKey().toString(),userEntity1.getHeader());
        }

        return resultMap;
    }

    @Override
    public UserVo queryUserPasswordByUsername(String userName) {


        UserEntity selectOneAnd = accountMapper.selectPasswordByUserName(userName);

        UserVo userVo = new UserVo();
        userVo.setUsername(userName);
        userVo.setPassword(selectOneAnd.getPassword());

        return userVo;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = {RuntimeException.class,Exception.class})
    public UserVo queryUserInfoByToken(String token) {


        UserVo userVo = new UserVo();

        String userId = TokenUtil.getObjectByToken(Base64.getDecoder().decode(token));

        UserEntity userEntity = new UserEntity();
        userEntity.setId(Long.valueOf(userId));
        UserEntity user = accountMapper.selectOneAnd(userEntity);

        BeanUtils.copyProperties(user,userVo);


        userEntity.setLastLoginTime(new Date());
        userEntity.setIntegration(user.getIntegration()+1);
        accountMapper.updateUserById(userEntity);

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
    public UserVo queryUserInfoByToken_1(String token) {

        UserVo userVo = new UserVo();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("token",token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<Map> post = restTemplate.postForEntity(PublicConfigUtil.OAUTHCHECKTOKENURI, request, Map.class);

        Map body = post.getBody();
        String userName = (String) body.get("user_name");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userName);
        UserEntity user = accountMapper.selectOneAnd(userEntity);
        BeanUtils.copyProperties(user,userVo);

        return userVo;
    }
}
