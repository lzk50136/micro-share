package online.reiam.share.service;

import java.util.List;

public interface UserCustomService {

    List<String> findRoleNameListByUserId(Integer userId);

    List<String> findPermissionNameListByRoleName(String roleName);

    void signUp(String username, String password, String roleName);

}
