package com.hslzk.share.controller;

import com.hslzk.share.constants.Constants;
import com.hslzk.share.entity.UserInfo;
import com.hslzk.share.jwt.JwtTokenUtil;
import com.hslzk.share.request.UserFollowRequest;
import com.hslzk.share.service.UserFollowService;
import com.hslzk.share.service.UserInfoService;
import com.hslzk.share.util.ApiResult;
import com.hslzk.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_follow")
public class UserFollowController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserFollowService userFollowService;

    /**
     * 关注/取消关注
     */
    @PostMapping(value = "/follow", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult follow(@RequestBody @Validated(UserFollowRequest.Follow.class) UserFollowRequest userFollowRequest, @RequestHeader("Authorization") String authorization) {
        UserInfo userInfo = userInfoService.exist(userFollowRequest.getNickname());
        userFollowService.follow(JwtTokenUtil.getUserId(authorization), userInfo.getUserId(), userFollowRequest);
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 粉丝列表
     */
    @PostMapping(value = "/list_user_follower", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult listUserFollower(@RequestBody @Validated(UserFollowRequest.ListFollow.class) UserFollowRequest userFollowRequest) {
        UserInfo userInfo = userInfoService.exist(userFollowRequest.getNickname());
        return ApiResultUtil.success(userFollowService.listUserFollowerByUserId(userFollowRequest, userInfo.getUserId()));
    }

    /**
     * 关注列表
     */
    @PostMapping(value = "/list_user_following", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult listUserFollowing(@RequestBody @Validated(UserFollowRequest.ListFollow.class) UserFollowRequest userFollowRequest) {
        UserInfo userInfo = userInfoService.exist(userFollowRequest.getNickname());
        return ApiResultUtil.success(userFollowService.listUserFollowingByUserId(userFollowRequest, userInfo.getUserId()));
    }

}
