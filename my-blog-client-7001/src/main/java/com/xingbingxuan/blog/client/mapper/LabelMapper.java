package com.xingbingxuan.blog.client.mapper;

import com.xingbingxuan.blog.client.entity.LabelEntity;
import com.xingbingxuan.blog.vo.LabelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/21 20:34
 */
@Mapper
public interface LabelMapper {

    public List<LabelEntity> selectAll();

    /**
     * 功能描述:
     * <p>根据博客id产询它的标签</p>
     *
     * @param blogId
     * @return : java.util.List<com.xingbingxuan.blog.vo.LabelVo>
     * @author : xbx
     * @date : 2022/8/4 23:18
     */
    public List<LabelVo> selectAllByBlogId(@Param("blogId") Integer blogId);
}
