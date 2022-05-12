package com.xingbingxuan.blog.client.mapper;

import com.xingbingxuan.blog.client.entity.SeriesEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/5 19:24
 */
@Mapper
public interface SeriesMapper {

    /**
     * 功能描述:
     * <p>查询所有的类别</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.SeriesEntity>
     *
     * @author : xbx
     * @date : 2022/4/6 22:47
     */
    public List<SeriesEntity> selectAll();

    /**
     * 功能描述:
     * <p>根据blogid 查询blog所有的标签</p>
     *
     * @param blogId
     * @return : com.xingbingxuan.blog.client.entity.SeriesEntity
     * @author : xbx
     * @date : 2022/5/3 13:31
     */
    public SeriesEntity selectAllByBId(Integer blogId);

}
