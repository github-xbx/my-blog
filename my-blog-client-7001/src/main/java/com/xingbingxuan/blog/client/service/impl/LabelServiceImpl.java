package com.xingbingxuan.blog.client.service.impl;

import com.xingbingxuan.blog.client.entity.LabelEntity;
import com.xingbingxuan.blog.client.mapper.LabelMapper;
import com.xingbingxuan.blog.client.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/21 20:41
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper labelMapper;

    @Override
    public List<LabelEntity> queryLabelAll() {

        return labelMapper.selectAll();
    }
}
