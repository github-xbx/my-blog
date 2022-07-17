package com.xingbingxuan.blog.client.service.impl;

import com.xingbingxuan.blog.client.entity.CategoryEntity;
import com.xingbingxuan.blog.client.mapper.CategoryMapper;

import com.xingbingxuan.blog.client.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/6 22:51
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryEntity> all() {
        return categoryMapper.selectAll();
    }
}
