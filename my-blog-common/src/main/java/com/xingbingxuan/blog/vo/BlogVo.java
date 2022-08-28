package com.xingbingxuan.blog.vo;


import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 博客vo 包括blog label series
 * @author : xbx
 * @date : 2022/4/5 11:28
 */
@Data
@ToString
public class BlogVo {

    /*博客*/
    private Integer blogId;
    private Integer blogUid;
    private String blogTitle;
    private String blogDescription;
    private String blogContent;
    private String blogImg;
    private Integer blogState;



    private Date blogInsertTime;
    private Integer blogTid;

    /*类别*/
    private Integer categoryId;
    private String categoryName;

    /*标签*/
    private List<LabelVo> label;

    /*用户头像*/
    private String userHeader;

    /*首页推荐设置字段*/
    private Integer indexSetting;

    /*阅读次数*/
    private Integer readCount;
}
