package com.xingbingxuan.blog.client.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author : xbx
 * @date : 2022/5/16 22:20
 */
@Data

public class CommentVo {

    private Integer commentId;

    private Integer commentUid;

    private String commentName;

    private Integer commentBid;

    private String commentContent;

    private Integer commentParentId;

    private Integer commentFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commentTime;

    private String bTitle;
}
