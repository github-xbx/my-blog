package com.xingbingxuan.blog.thirdparty.gitee;

import com.xingbingxuan.blog.config.PublicConfigUtil;
import com.xingbingxuan.blog.thirdparty.service.ThirdAuth;
import me.zhyd.oauth.model.AuthCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 第三方回调的controller
 * @author : xbx
 * @date : 2022/7/24 22:18
 */
@Controller
public class ThirdCallbackController {

    @Autowired
    private ThirdAuth thirdAuth;

    /**
     * 功能描述:
     * <p>gitee的回调</p>
     *
     * @param callback
     * @return : org.springframework.web.servlet.ModelAndView
     * @author : xbx
     * @date : 2022/7/24 22:17
     */
    @RequestMapping("callback/{callbackType}")
    public ModelAndView getToken(AuthCallback callback, @PathVariable(name = "callbackType") String callbackType){

        ModelAndView modelAndView = new ModelAndView();


        //判断具体的第三方登录类型
        switch (callbackType){
            case "gitee":

                Map<String, Object> login = thirdAuth.giteeLogin(callback);

                if ("success".equals(login.get("login"))){
                    modelAndView.setViewName("login");
                    //System.out.println(login.get("userInfo"));
                    modelAndView.addObject("userInfo",login.get("userInfo"));
                    modelAndView.addObject("domain", PublicConfigUtil.BLOG_HOME_DOMAIN);
                }else {
                    modelAndView.setViewName("error");
                    modelAndView.addObject("message","登录错误");
                }

                break;
            default:
                modelAndView.setViewName("error");
                modelAndView.addObject("message","没有该类型的授权登录！！！");
                break;
        }
        return modelAndView;




    }
}
