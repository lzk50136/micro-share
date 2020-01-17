package com.hslzk.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hslzk.share.entity.Likes;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 点赞表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface LikesMapper extends BaseMapper<Likes> {

    IPage<Integer> selectUserIdListByLikesAndType(Page page, @Param("typeId") Integer typeId, @Param("likesType") Integer likesType);

}
