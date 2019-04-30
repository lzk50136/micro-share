package online.reiam.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.Post;
import online.reiam.share.request.PostRequest;
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

    Post postExist(Integer postId);

    PostResponse addPost(Integer userId, PostRequest postRequest);

    void removePostById(Integer userId, Integer postId);

    IPage<PostResponse> listPostByUserId(PostRequest postRequest);

    IPage<PostResponse> listPostByFollowId(Integer userId, PostRequest postRequest);

}
