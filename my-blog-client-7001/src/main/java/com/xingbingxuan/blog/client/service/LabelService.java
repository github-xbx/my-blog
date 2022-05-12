package com.xingbingxuan.blog.client.service;


import com.xingbingxuan.blog.client.entity.LabelEntity;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/21 20:40
 */
public interface LabelService {

    List<LabelEntity> queryLabelAll();
}
