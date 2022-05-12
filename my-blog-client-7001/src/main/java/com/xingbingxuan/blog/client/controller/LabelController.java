package com.xingbingxuan.blog.client.controller;

import com.xingbingxuan.blog.client.entity.LabelEntity;
import com.xingbingxuan.blog.client.entity.LabelEntity;
import com.xingbingxuan.blog.client.service.LabelService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/21 20:43
 */
@RestController
@RequestMapping("blogClient")
public class LabelController {


    @Autowired
    private LabelService labelService;

    @RequestMapping("queryLabelAll")
    public Result<List<LabelEntity>> queryLabelAll(){

        List<LabelEntity> labels = labelService.queryLabelAll();

        return Result.success(labels);
    }
}
