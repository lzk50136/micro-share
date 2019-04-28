package online.reiam.share.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.UserInfoRequest;
import online.reiam.share.response.UserInfoResponse;
import online.reiam.share.service.UserInfoService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/user_info")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取用户信息
     */
    @RequiresRoles("user")
    @PostMapping(value = "/get_user_info", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult getUserInfo(@RequestBody @Validated(UserInfoRequest.GetUserInfo.class) UserInfoRequest userInfoRequest, @RequestHeader("Authorization") String authorization) {
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getNickname, userInfoRequest.getNickname()));
        if (userInfo == null) {
            throw new MicroShareException(10008, "用户不存在。");
        }
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        // 判断是否本人获取，他人获取和本人获取看到的信息部分不一样
        if (userInfo.getUserId().equals(JwtTokenUtil.getUserId(authorization))) {
            BeanUtils.copyProperties(userInfo, userInfoResponse);
        } else {
            BeanUtils.copyProperties(userInfo, userInfoResponse);
        }
        return ApiResultUtil.success(userInfoResponse);
    }

    /**
     * 修改用户信息
     */
    @RequiresRoles("user")
    @PostMapping(value = "/update_user_info", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult updateUserInfo(@RequestBody @Validated UserInfoRequest userInfoRequest, @RequestHeader("Authorization") String authorization) {
        UserInfo userInfo2 = new UserInfo();
        if (userInfoRequest.getNickname() != null) {
            if (userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getNickname, userInfoRequest.getNickname())) != null) {
                throw new MicroShareException(10014, "昵称已被占用。");
            }
            userInfo2.setNickname(userInfoRequest.getNickname());
        }
        if (userInfoRequest.getGender() != null) {
            userInfo2.setGender(userInfoRequest.getGender());
        }
        if (userInfoRequest.getWebsite() != null) {
            userInfo2.setWebsite(userInfoRequest.getWebsite());
        }
        if (userInfoRequest.getBio() != null) {
            userInfo2.setBio(userInfoRequest.getBio());
        }
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, JwtTokenUtil.getUserId(authorization)));
        userInfo2.setId(userInfo.getId())
                .setVersion(userInfo.getVersion())
                .setModifiedTime(LocalDateTime.now());
        if (!userInfoService.updateById(userInfo2)) {
            throw new MicroShareException(10001, "操作失败。");
        }
        return ApiResultUtil.success("操作成功。");
    }

}
