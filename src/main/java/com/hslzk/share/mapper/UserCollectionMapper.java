package com.hslzk.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hslzk.share.entity.UserCollection;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户收藏表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface UserCollectionMapper extends BaseMapper<UserCollection> {

    IPage<Integer> selectPostIdListByUserId(Page page, @Param("userId") Integer userId);

}
