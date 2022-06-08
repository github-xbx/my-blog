package com.xingbingxuan.blog.client.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingbingxuan.blog.client.entity.LabelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date blogInsertTime;
    private Integer blogTid;

    /*类别*/
    private Integer categoryId;
    private String categoryName;

    /*标签*/
    private List<LabelEntity> label;
//    private Integer labelId;
//    private String labelName;


}
