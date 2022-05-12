package com.xingbingxuan.blog.client.controller;

import com.xingbingxuan.blog.client.entity.SeriesEntity;
import com.xingbingxuan.blog.client.service.SeriesService;
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
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping("allSeries")
    public Result<List<SeriesEntity>> allSeries(){

        List<SeriesEntity> all = seriesService.all();
        return Result.success(all);
    }
}
