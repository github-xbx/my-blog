package com.xingbingxuan.blog.account.controller;

import cn.hutool.json.JSON;
import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.annotation.Role;
import com.xingbingxuan.blog.param.UserParam;
import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户操作控制器
 * @author : xbx
 * @date : 2022/5/2 9:48
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private AccountService accountService;

    @PostMapping("userPage")
    public Result<PageInfo<UserEntity>> queryAllUserPage(@RequestBody Map map){

        PageInfo<UserEntity> pageInfo = accountService.queryAllUserPage((Integer) map.get("pageNum"), (Integer) map.get("pageSize"));

        return Result.success(pageInfo);
    }

    /**
     * 功能描述:
     * <p>获取注册用户的个数</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result<java.lang.Integer>
     * @author : xbx
     * @date : 2022/5/14 17:55
     */
    @GetMapping("accountCount")
    @PreAuthorize("hasRole('ROLE_BLOG_USER')")
    public Result<Integer> queryAccountCount(){
        Integer count = accountService.queryAccountCount();
        return Result.success(count);
    }

    /**
     * 功能描述:
     * <p>获得最近七天注册的用户数</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/5/14 17:57
     */
    @GetMapping("accountByWeek")
    public Result accountByWeek(){

        List list = accountService.queryAccountCountByThisWeek();

        return Result.success(list);
    }

    /**
     * 功能描述:
     * <p>根据用户的id集合查询各自的头像信息</p>
     * @param blogIdAndUserId
     * @return : java.util.List<com.xingbingxuan.blog.account.entity.vo.UserVo>
     * @author : xbx
     * @date : 2022/6/8 22:47
     */
    @PostMapping("queryUserHeaderByIds")
    public Map queryUserHeaderByIds(@RequestBody Map<Integer,Integer> blogIdAndUserId){

        Map map = accountService.queryUserHeaderByIds(blogIdAndUserId);

        return map;
    }

    /**
     * 功能描述:
     * <p>根据token获取用户的信息</p>
     *
     * @param request
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/7/11 22:37
     */
    @GetMapping("userInfo")
    @Role(value = {"ROLE_BLOG_USER"})
    public Result queryUserInfo(String token){

        log.info(token);
        UserVo userVo = accountService.queryUserInfoByToken(token);

        return Result.success(userVo);
    }

    /**
     * 功能描述:
     * <p>用户的登出操作</p>
     *
     * @param request
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/7/17 22:58
     */
    @PostMapping("logout")
    @Role(value = {"ROLE_BLOG_USER"})
    public Result userLogout(){

        Boolean logout = accountService.logout();
        if (logout){
            return Result.success(true);
        }else {
            return Result.error();
        }

    }

    /**
     * 功能描述:
     * <p>用户登录操作</p>
     *
     * @param param
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/10/1 11:02
     */
    @PostMapping("login")
    public Result userLogin(@RequestBody Map<String,String> param){

        JSON login = accountService.userLogin(param);

        return Result.success(login);
    }

    /**
     * 功能描述:
     * <p>第三方服务调用</p>
     *
     * @param param
     * @return : com.xingbingxuan.blog.vo.UserVo
     * @author : xbx
     * @date : 2022/7/25 22:28
     */
    @Deprecated
    @PostMapping("thirdLogin")
    public UserVo loginAndRegister(@RequestBody Map param){
        UserVo account = this.accountService.isAccount(param);
        //密码脱敏
        account.setPassword(null);
        return account;
    }

}
