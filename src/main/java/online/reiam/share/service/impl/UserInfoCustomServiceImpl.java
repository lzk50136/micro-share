package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.request.UserInfoRequest;
import online.reiam.share.response.UserInfoResponse;
import online.reiam.share.service.UserInfoCustomService;
import online.reiam.share.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserInfoCustomServiceImpl implements UserInfoCustomService {
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserInfo userExist(String nickname) {
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getNickname, nickname));
        if (userInfo == null) {
            throw new MicroShareException(10008, "用户不存在。");
        }
        return userInfo;
    }

    @Override
    public UserInfoResponse getUserInfoResponse(UserInfo userInfo, Integer userId) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        // 判断是否本人获取，他人获取和本人获取看到的信息部分不一样
        if (userInfo.getUserId().equals(userId)) {
            BeanUtils.copyProperties(userInfo, userInfoResponse);
        } else {
            BeanUtils.copyProperties(userInfo, userInfoResponse);
        }
        return userInfoResponse;
    }

    @Override
    public void updateUserInfo(UserInfoRequest userInfoRequest, Integer userId) {
        UserInfo userInfo2 = new UserInfo();
        if (userInfoRequest.getNickname() != null) {
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(UserInfo::getNickname, userInfoRequest.getNickname());
            if (userInfoService.getOne(queryWrapper) != null) {
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
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, userId));
        userInfo2.setId(userInfo.getId())
                .setVersion(userInfo.getVersion())
                .setModifiedTime(LocalDateTime.now());
        if (!userInfoService.updateById(userInfo2)) {
            throw new MicroShareException(10001, "操作失败。");
        }
    }

}
