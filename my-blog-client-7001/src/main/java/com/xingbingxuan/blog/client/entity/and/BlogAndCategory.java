package com.xingbingxuan.blog.client.entity.and;

import com.xingbingxuan.blog.client.entity.BlogEntity;
import com.xingbingxuan.blog.client.entity.CategoryEntity;
import lombok.Data;
import lombok.ToString;

/**
 * @author : xbx
 * @date : 2022/4/6 17:24
 */
@Data
@ToString
public class BlogAndCategory {

    private BlogEntity blogEntity;

    private CategoryEntity categoryEntity;
}
