package online.reiam.share.controller;

import online.reiam.share.entity.UserInfo;
import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.UserFollowRequest;
import online.reiam.share.service.UserFollowCustomService;
import online.reiam.share.service.UserInfoCustomService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/user_follow")
public class UserFollowController {
    @Autowired
    private UserFollowCustomService userFollowCustomService;
    @Autowired
    private UserInfoCustomService userInfoCustomService;

    /**
     * 关注/取消关注
     */
    @PostMapping(value = "/follow", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult follow(@RequestBody @Validated(UserFollowRequest.Follow.class) UserFollowRequest userFollowRequest, @RequestHeader("Authorization") String authorization) {
        UserInfo userInfo = userInfoCustomService.userExist(userFollowRequest.getNickname());
        userFollowCustomService.follow(JwtTokenUtil.getUserId(authorization), userInfo.getUserId(), userFollowRequest);
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 粉丝列表
     */
    @PostMapping(value = "/list_user_follower", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listUserFollower(@RequestBody @Validated(UserFollowRequest.ListFollow.class) UserFollowRequest userFollowRequest) {
        UserInfo userInfo = userInfoCustomService.userExist(userFollowRequest.getNickname());
        return ApiResultUtil.success(userFollowCustomService.listUserFollowerByUserId(userFollowRequest, userInfo.getUserId()));
    }

    /**
     * 关注列表
     */
    @PostMapping(value = "/list_user_following", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listUserFollowing(@RequestBody @Validated(UserFollowRequest.ListFollow.class) UserFollowRequest userFollowRequest) {
        UserInfo userInfo = userInfoCustomService.userExist(userFollowRequest.getNickname());
        return ApiResultUtil.success(userFollowCustomService.listUserFollowingByUserId(userFollowRequest, userInfo.getUserId()));
    }

}
