package online.reiam.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.Post;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.request.PostRequest;
import online.reiam.share.response.LikesPostResponse;
import online.reiam.share.response.PostAtResponse;
import online.reiam.share.response.PostResponse;

/**
 * <p>
 * 用户贴子表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface PostService extends IService<Post> {

    PostResponse addPost(Integer userId, PostRequest postRequest);

    void removePostById(Integer userId, Integer postId);

    IPage<PostResponse> listPostByUserId(PostRequest postRequest, UserInfo userInfo);

    IPage<PostResponse> listPostByTopicId(PostRequest postRequest);

    IPage<PostResponse> listPostByFollowId(Integer userId, PostRequest postRequest);

    IPage<PostAtResponse> listPostByAtMe(PostRequest postRequest, Integer userId);

    IPage<LikesPostResponse> listPostByLikeMe(PostRequest postRequest, Integer userId);

}
