package online.reiam.share.service;

import online.reiam.share.entity.UserInfo;
import online.reiam.share.request.UserInfoRequest;
import online.reiam.share.response.UserInfoResponse;

public interface UserInfoCustomService {

    UserInfo userExist(String nickname);

    UserInfoResponse getUserInfoResponse(UserInfo userInfo, Integer userId);

    void updateUserInfo(UserInfoRequest userInfoRequest, Integer userId);

}
