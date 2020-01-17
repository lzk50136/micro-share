package com.hslzk.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hslzk.share.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface UserMapper extends BaseMapper<User> {

    List<String> selectRoleNameListByUserId(@Param("userId") Integer userId);

    List<String> selectPermissionNameListByRoleName(@Param("roleName") String roleName);

}
