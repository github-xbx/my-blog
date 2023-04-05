package com.xingbingxuan.blog.account.mapper;

import com.xingbingxuan.blog.account.entity.UserFollowEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/6/20 23:06
 */
@Mapper
public interface UserFollowMapper {

    /**
     * 功能描述:
     * <p>根据用户id，查询关注的用户id</p>
     *
     * @param userId
     * @return : java.util.List<com.xingbingxuan.blog.account.entity.UserFollowEntity>
     * @author : xbx
     * @date : 2022/6/20 23:07
     */
    public List<UserFollowEntity> selectAllByUserId(Integer userId);

    /**
     * 功能描述:
     * <p>查询用户的关注数</p>
     *
     * @param userId
     * @return : java.lang.Integer
     * @author : xbx
     * @date : 2022/6/20 23:18
     */
    public Integer selectCountByUserId(Integer userId);
    /**
     * 功能描述:
     * <p>根据被关注的id 查询用户id，</p>
     *
     * @param followUserId
     * @return : java.util.List<com.xingbingxuan.blog.account.entity.UserFollowEntity>
     * @author : xbx
     * @date : 2022/6/20 23:11
     */
    public List<UserFollowEntity> selectAllByFollowUserId(Integer followUserId);

    /**
     * 功能描述:
     * <p>查询用户的被关注数（粉丝数）</p>
     *
     * @param followUserId
     * @return : java.lang.Integer
     * @author : xbx
     * @date : 2022/6/20 23:19
     */
    public Integer selectCountByFollowUserId(Integer followUserId);
}
