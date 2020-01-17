package com.hslzk.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hslzk.share.response.PostDetailResponse;
import com.hslzk.share.entity.PostDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 贴子详情表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface PostDetailMapper extends BaseMapper<PostDetail> {

    List<PostDetailResponse> selectPostDetailListByPostId(@Param("postId") Integer postId);

}
