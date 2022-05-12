package com.xingbingxuan.blog.client.service.impl;

import com.xingbingxuan.blog.client.entity.SeriesEntity;
import com.xingbingxuan.blog.client.mapper.SeriesMapper;
import com.xingbingxuan.blog.client.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/6 22:51
 */
@Service
public class SeriesServiceImpl implements SeriesService {

    @Autowired
    private SeriesMapper seriesMapper;

    @Override
    public List<SeriesEntity> all() {
        return seriesMapper.selectAll();
    }
}
