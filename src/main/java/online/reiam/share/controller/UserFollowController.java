package online.reiam.share.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.UserFollowRequest;
import online.reiam.share.service.UserFollowCustomService;
import online.reiam.share.service.UserInfoService;
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
    private UserInfoService userInfoService;

    /**
     * 关注/取消关注
     */
    @PostMapping(value = "/follow", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult follow(@RequestBody @Validated(UserFollowRequest.Follow.class) UserFollowRequest userFollowRequest, @RequestHeader("Authorization") String authorization) {
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getNickname, userFollowRequest.getNickname()));
        if (userInfo == null) {
            throw new MicroShareException(10008, "用户不存在。");
        }
        Integer userId = JwtTokenUtil.getUserId(authorization);
        if (userInfo.getUserId().equals(userId)) {
            throw new MicroShareException(10015, "不能自己关注自己。");
        }
        userFollowCustomService.follow(userId, userInfo, userFollowRequest);
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 粉丝列表
     */
    @PostMapping(value = "/list_follower_by_user_id", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listFollowerByUserId(@RequestBody @Validated(UserFollowRequest.ListFollow.class) UserFollowRequest userFollowRequest) {
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getNickname, userFollowRequest.getNickname()));
        if (userInfo == null) {
            throw new MicroShareException(10008, "用户不存在。");
        }
        return ApiResultUtil.success(userFollowCustomService.findUserFollowerListByUserId(userFollowRequest, userInfo.getUserId()));
    }

    /**
     * 关注列表
     */
    @PostMapping(value = "/list_following_by_user_id", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listFollowingByUserId(@RequestBody @Validated(UserFollowRequest.ListFollow.class) UserFollowRequest userFollowRequest) {
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getNickname, userFollowRequest.getNickname()));
        if (userInfo == null) {
            throw new MicroShareException(10008, "用户不存在。");
        }
        return ApiResultUtil.success(userFollowCustomService.findUserFollowingListByUserId(userFollowRequest, userInfo.getUserId()));
    }

}
