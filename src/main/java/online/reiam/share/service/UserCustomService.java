package online.reiam.share.service;

import online.reiam.share.entity.User;

import java.util.List;

public interface UserCustomService {

    List<String> listRoleNameByUserId(Integer userId);

    List<String> listPermissionNameByRoleName(String roleName);

    void userExist(String username);

    User userNotExist(String username);

    void validateCode(String code1, String code2);

    void signUp(String username, String password, String roleName);

}
