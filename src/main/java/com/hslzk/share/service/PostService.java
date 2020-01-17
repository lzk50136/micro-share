package com.hslzk.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hslzk.share.entity.Post;
import com.hslzk.share.request.PostRequest;
import com.hslzk.share.response.PostResponse;

/**
 * <p>
 * 贴子表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface PostService extends IService<Post> {

    Post exist(Integer postId);

    PostResponse create(Integer userId, PostRequest postRequest);

    void delete(Integer userId, Integer postId);

    IPage<PostResponse> listPostByUserId(PostRequest postRequest);

    IPage<PostResponse> listPostByFollowId(Integer userId, PostRequest postRequest);

}
