package com.hslzk.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hslzk.share.response.UserFollowerResponse;
import com.hslzk.share.response.UserFollowingResponse;
import com.hslzk.share.entity.UserFollow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户关注表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface UserFollowMapper extends BaseMapper<UserFollow> {

    List<Integer> selectFollowIdListByUserId(@Param("userId") Integer userId);

    IPage<UserFollowerResponse> selectUserFollowerListByUserId(Page page, @Param("userId") Integer userId);

    IPage<UserFollowingResponse> selectUserFollowingListByUserId(Page page, @Param("userId") Integer userId);

}
