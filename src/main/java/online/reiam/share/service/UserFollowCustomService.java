package online.reiam.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import online.reiam.share.entity.UserFollow;
import online.reiam.share.request.UserFollowRequest;
import online.reiam.share.response.UserFollowerResponse;
import online.reiam.share.response.UserFollowingResponse;

public interface UserFollowCustomService {

    UserFollow getFollow(Integer userId, Integer followId);

    void follow(Integer userId, Integer followId, UserFollowRequest userFollowRequest);

    IPage<UserFollowerResponse> listUserFollowerByUserId(UserFollowRequest userFollowRequest, Integer userId);

    IPage<UserFollowingResponse> listUserFollowingByUserId(UserFollowRequest userFollowRequest, Integer userId);

}
