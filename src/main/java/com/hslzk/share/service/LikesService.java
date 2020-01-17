package com.hslzk.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hslzk.share.entity.Likes;
import com.hslzk.share.request.LikesRequest;
import com.hslzk.share.response.UserInfoResponse;

/**
 * <p>
 * 点赞表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface LikesService extends IService<Likes> {

    void likes(Integer userId, LikesRequest likesRequest);

    IPage<UserInfoResponse> listUserInfoByLikes(LikesRequest likesRequest);

}
