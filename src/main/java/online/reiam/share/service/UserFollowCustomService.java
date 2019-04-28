package online.reiam.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.request.UserFollowRequest;
import online.reiam.share.response.UserFollowerResponse;
import online.reiam.share.response.UserFollowingResponse;

public interface UserFollowCustomService {

    void follow(Integer userId, UserInfo userInfo, UserFollowRequest userFollowRequest);

    IPage<UserFollowerResponse> findUserFollowerListByUserId(UserFollowRequest userFollowRequest, Integer userId);

    IPage<UserFollowingResponse> findUserFollowingListByUserId(UserFollowRequest userFollowRequest, Integer userId);

}
