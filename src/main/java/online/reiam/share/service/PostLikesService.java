package online.reiam.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.Post;
import online.reiam.share.entity.PostLikes;
import online.reiam.share.request.PostLikesRequest;
import online.reiam.share.response.PostLikesResponse;

/**
 * <p>
 * 贴子点赞表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface PostLikesService extends IService<PostLikes> {

    void likes(Integer userId, PostLikesRequest postLikesRequest, Post post);

    IPage<PostLikesResponse> selectPostLikes(PostLikesRequest postLikesRequest);

}
