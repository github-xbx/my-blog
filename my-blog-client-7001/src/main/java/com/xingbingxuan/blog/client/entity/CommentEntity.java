package com.xingbingxuan.blog.client.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * 评论实体类
 * @author : xbx
 * @date : 2022/4/7 16:38
 */
@Data
@ToString
public class CommentEntity {

    @Id
    private Integer commentId;

    private Integer commentUid;

    private String commentName;

    private Integer commentBid;

    private String commentContent;

    private Integer commentParentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commentTime;

    private List<CommentEntity> children;
}
