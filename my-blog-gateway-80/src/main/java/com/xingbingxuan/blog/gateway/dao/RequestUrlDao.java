package com.xingbingxuan.blog.gateway.dao;

import com.xingbingxuan.blog.gateway.entity.RequestUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author : xbx
 * @date : 2023/4/5 15:29
 */
@Repository
public interface RequestUrlDao extends CrudRepository<RequestUrl,Integer> {

    /**
     * 功能描述:
     * <p>根据请求url查询信息</p>
     *
     * @param requestUrl
     * @return : Integer
     * @author : xbx
     * @date : 2023/4/5 15:38
     */
    RequestUrl findByRequestUrl(String requestUrl);


}
