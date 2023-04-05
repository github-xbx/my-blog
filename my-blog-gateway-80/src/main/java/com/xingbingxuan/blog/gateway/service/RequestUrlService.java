package com.xingbingxuan.blog.gateway.service;

import com.xingbingxuan.blog.gateway.dao.RequestUrlDao;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : xbx
 * @date : 2023/4/5 16:10
 */
@Service
public class RequestUrlService {

    @Autowired
    private RequestUrlDao requestUrlDao;


    public Boolean getRequestIsAuth(String requestUrl){

        return null;
    }
}
