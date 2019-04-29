package online.reiam.share.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.response.LikesPostResponse;
import online.reiam.share.response.PostLikesResponse;
import org.apache.ibatis.annotations.Param;

public interface PostLikesCustomMapper {

    IPage<LikesPostResponse> selectLikesPostListByUserId(Page page, @Param("userId") Integer userId);

    IPage<PostLikesResponse> selectPostLikesListByPostId(Page page, @Param("postId") Integer postId);

}
