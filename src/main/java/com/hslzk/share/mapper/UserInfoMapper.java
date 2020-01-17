package com.hslzk.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hslzk.share.entity.UserInfo;
import com.hslzk.share.response.UserInfoResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfoResponse selectUserInfoByUserId(@Param("userId") Integer userId);

    List<UserInfoResponse> selectUserInfoListByUserIdList(List<Integer> list);

}
