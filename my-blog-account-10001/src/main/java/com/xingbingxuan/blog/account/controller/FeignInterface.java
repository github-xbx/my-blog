package com.xingbingxuan.blog.account.controller;

import com.xingbingxuan.blog.account.entity.bo.UserAndRoleRelation;
import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.param.UserParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 账户服务feign开放接口
 * @author : xbx
 * @date : 2022/9/3 18:17
 */
@RestController
@RequestMapping("userFeignInterface")
public class FeignInterface {

    @Autowired
    private AccountService accountService;


    /**
     * 功能描述:
     * <p>授权服务器调用，用于登录， </p>
     *
     * @param userName
     * @return : com.xingbingxuan.blog.utils.Result<com.xingbingxuan.blog.dto.UserAllInfoDto>
     * @author : xbx
     * @date : 2022/9/3 18:14
     */
    @PostMapping("/userLogin")
    public UserAllInfoDto userLogin(@RequestBody String userName){

        UserAllInfoDto userAllInfoDto = accountService.queryUserPasswordByUsername(userName);

        return userAllInfoDto;

    }

    /**
     * 功能描述:
     * <p>根据第三方的类型和uid在用户表中查询是否有该用户，没有就添加一个</p>
     *
     * @param userParam
     * @return : com.xingbingxuan.blog.dto.UserAllInfoDto
     * @author : xbx
     * @date : 2022/9/5 22:49
     */
    @PostMapping("/thirdPartyLogin")
    public UserAllInfoDto thirdPartyLogin(@RequestBody UserParam userParam){

        UserAndRoleRelation userAndRoleRelation = accountService.selectOrSaveUserBySocialUidAndSocialType(userParam);

        UserAllInfoDto result = new UserAllInfoDto();

        BeanUtils.copyProperties(userAndRoleRelation,result);

        return result;
    }


}
