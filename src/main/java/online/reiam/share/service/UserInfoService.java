package online.reiam.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.request.UserInfoRequest;
import online.reiam.share.response.UserInfoResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface UserInfoService extends IService<UserInfo> {

    UserInfo userExist(String nickname);

    UserInfoResponse getUserInfoResponse(UserInfo userInfo, Integer userId);

    void updateUserInfo(UserInfoRequest userInfoRequest, Integer userId);

}
