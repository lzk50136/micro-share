package online.reiam.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.entity.PostLikes;
import online.reiam.share.response.LikesPostResponse;
import online.reiam.share.response.PostLikesResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 贴子点赞表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface PostLikesMapper extends BaseMapper<PostLikes> {

    IPage<LikesPostResponse> selectLikesPostListByUserId(Page page, @Param("userId") Integer userId);

    IPage<PostLikesResponse> selectPostLikesListByPostId(Page page, @Param("postId") Integer postId);

}
