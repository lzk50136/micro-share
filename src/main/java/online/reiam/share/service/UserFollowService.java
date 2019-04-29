package online.reiam.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.UserFollow;
import online.reiam.share.request.UserFollowRequest;
import online.reiam.share.response.UserFollowerResponse;
import online.reiam.share.response.UserFollowingResponse;

/**
 * <p>
 * 用户关注表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface UserFollowService extends IService<UserFollow> {

    UserFollow getFollow(Integer userId, Integer followId);

    void follow(Integer userId, Integer followId, UserFollowRequest userFollowRequest);

    IPage<UserFollowerResponse> listUserFollowerByUserId(UserFollowRequest userFollowRequest, Integer userId);

    IPage<UserFollowingResponse> listUserFollowingByUserId(UserFollowRequest userFollowRequest, Integer userId);

}
