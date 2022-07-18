package com.xingbingxuan.blog.client.controller;

import com.xingbingxuan.blog.client.entity.CategoryEntity;
import com.xingbingxuan.blog.client.service.CategoryService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 类别控制器
 * @author : xbx
 * @date : 2022/4/6 22:43
 */
@RestController
@RequestMapping("blogClient")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("queryCategoryAll")
    public Result<List<CategoryEntity>> queryCategoryAll(){

        List<CategoryEntity> all = categoryService.all();
        return Result.success(all);
    }
}
