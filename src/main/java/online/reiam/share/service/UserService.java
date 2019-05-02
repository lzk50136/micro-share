package online.reiam.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.User;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface UserService extends IService<User> {

    List<String> listRoleNameByUserId(Integer userId);

    List<String> listPermissionNameByRoleName(String roleName);

    User exist(String username);

    void validateCode(String code1, String code2);

    void signUp(String username, String password, String roleName);

}
