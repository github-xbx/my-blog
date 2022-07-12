package com.xingbingxuan.blog.account.controller;

import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.utils.PublicConfigUtil;
import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * oauth2 用户处理controller
 * @author : xbx
 * @date : 2022/6/30 22:00
 */
@Slf4j
@RestController
@RequestMapping("oauth2")
public class Oauth2Controller {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountService accountService;

    //http://localhost:11001/oauth/authorize?client_id=blog&response_type=code&scope=all&redirect_uri=http://localhost:8080/oauthLogin
    /**
     * 功能描述:
     * <p>oauth 回调 获取oauth 的access_token</p>
     *
     * @param param
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/6/30 22:01
     */
    @PostMapping("/getToken")
    public Result oauthGetToken(@RequestBody Map param){

        log.info("参数 -> {}",param);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        HashMap<String, String> map = new HashMap<>(); N21BQR
        map.add("grant_type","authorization_code");
        map.add("code", (String) param.get("code"));
        map.add("client_id", PublicConfigUtil.CLIENTID);
        map.add("client_secret",PublicConfigUtil.CLIENTSECRET);
        map.add("redirect_uri",PublicConfigUtil.REDIRECTURI);
//        JdbcAuthorizationCodeServices
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<Map> post = restTemplate.postForEntity(PublicConfigUtil.OAUTHTOKENURI, request, Map.class);

        Map body = post.getBody();
        log.info("返回的token -> {}",body);


        return Result.success(body.get("access_token"));
    }


    @PostMapping("/userLogin")
    public Result<UserVo> userLogin(@RequestBody String userName){

        UserVo userVo = accountService.queryUserPasswordByUsername(userName);

        return Result.success(userVo);

    }

}
