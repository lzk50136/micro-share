package com.hslzk.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hslzk.share.request.UserInfoRequest;
import com.hslzk.share.entity.UserInfo;
import com.hslzk.share.response.UserInfoResponse;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface UserInfoService extends IService<UserInfo> {

    UserInfo exist(String nickname);

    UserInfoResponse getUserInfo(UserInfo userInfo, Integer userId);

    void updateUserInfo(UserInfoRequest userInfoRequest, Integer userId);

}
