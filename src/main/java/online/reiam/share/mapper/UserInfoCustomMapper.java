package online.reiam.share.mapper;

import online.reiam.share.response.UserInfoResponse;
import org.apache.ibatis.annotations.Param;

public interface UserInfoCustomMapper {

    UserInfoResponse selectUserInfoByUserId(@Param("userId") Integer userId);

}
