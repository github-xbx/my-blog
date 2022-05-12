package com.xingbingxuan.blog.client.entity.and;

import com.xingbingxuan.blog.client.entity.BlogEntity;
import com.xingbingxuan.blog.client.entity.SeriesEntity;
import lombok.Data;
import lombok.ToString;

/**
 * @author : xbx
 * @date : 2022/4/6 17:24
 */
@Data
@ToString
public class BlogAndSeries {

    private BlogEntity blogEntity;

    private SeriesEntity seriesEntity;
}
