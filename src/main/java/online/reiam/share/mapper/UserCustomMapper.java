package online.reiam.share.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCustomMapper {

    List<String> selectRoleNameListByUserId(@Param("userId") Integer userId);

    List<String> selectPermissionNameListByRoleName(@Param("roleName") String roleName);

}
