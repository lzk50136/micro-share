package online.reiam.share.controller;

import online.reiam.share.entity.UserInfo;
import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.UserInfoRequest;
import online.reiam.share.service.UserInfoCustomService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/user_info")
public class UserInfoController {
    @Autowired
    private UserInfoCustomService userInfoCustomService;

    /**
     * 获取用户信息
     */
    @RequiresRoles("user")
    @PostMapping(value = "/get_user_info", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult getUserInfo(@RequestBody @Validated(UserInfoRequest.GetUserInfo.class) UserInfoRequest userInfoRequest, @RequestHeader("Authorization") String authorization) {
        UserInfo userInfo = userInfoCustomService.userExist(userInfoRequest.getNickname());
        return ApiResultUtil.success(userInfoCustomService.getUserInfoResponse(userInfo, JwtTokenUtil.getUserId(authorization)));
    }

    /**
     * 修改用户信息
     */
    @RequiresRoles("user")
    @PostMapping(value = "/update_user_info", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult updateUserInfo(@RequestBody @Validated UserInfoRequest userInfoRequest, @RequestHeader("Authorization") String authorization) {
        userInfoCustomService.updateUserInfo(userInfoRequest, JwtTokenUtil.getUserId(authorization));
        return ApiResultUtil.success("操作成功。");
    }

}
