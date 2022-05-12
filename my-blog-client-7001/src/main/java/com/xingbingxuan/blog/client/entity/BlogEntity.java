package com.xingbingxuan.blog.client.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author : xbx
 * @date : 2022/4/4 9:28
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogEntity {

    @Id
    @JsonProperty("bId")
    private Integer bId;

    @NotNull(message = "用户id不能为空")
    @JsonProperty("bUid")
    private Integer bUid;

    @NotBlank(message = "标题不能为空")
    @JsonProperty("bTitle")
    private String bTitle;

    @NotBlank(message = "摘要不能为空")
    @JsonProperty("bDescription")
    private String bDescription;

    @NotBlank(message = "内容不能为空")
    @JsonProperty("bContent")
    private String bContent;

    @NotBlank(message = "图片不能为空")
    @JsonProperty("bImg")
    private String bImg;


    @JsonProperty("bState")
    @Builder.Default
    private Integer bState = 1;

    @NotNull(message = "时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("bInsertTime")
    private Date bInsertTime;

    @NotNull(message = "类别不能为空")
    @JsonProperty("bTid")
    private Integer bTid;

}
