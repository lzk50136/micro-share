package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.reiam.share.entity.Role;
import online.reiam.share.entity.User;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.entity.UserRole;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.UserCustomMapper;
import online.reiam.share.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserCustomServiceImpl implements UserCustomService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Resource
    private UserCustomMapper userCustomMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 获取角色列表
     */
    @Override
    public List<String> listRoleNameByUserId(Integer userId) {
        return userCustomMapper.selectRoleNameListByUserId(userId);
    }

    /**
     * 获取权限列表
     */
    @Override
    public List<String> listPermissionNameByRoleName(String roleName) {
        return userCustomMapper.selectPermissionNameListByRoleName(roleName);
    }

    /**
     * 用户已存在
     */
    @Override
    public void userExist(String username) {
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
        if (user != null) {
            throw new MicroShareException(10006, "用户已存在。");
        }
    }

    /**
     * 用户不存在
     */
    @Override
    public User userNotExist(String username) {
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
        if (user == null) {
            throw new MicroShareException(10008, "用户不存在。");
        }
        return user;
    }

    /**
     * 判断验证码
     */
    @Override
    public void validateCode(String code1, String code2) {
        if (!code1.equals(code2)) {
            throw new MicroShareException(10007, "验证码不正确。");
        }
    }

    /**
     * 注册服务
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void signUp(String username, String password, String roleName) {
        // 1、插入用户表
        User user = new User();
        user.setUsername(username)
                .setPassword(password)
                .setDisabled(false)
                .setDeleted(false)
                .setVersion(0)
                .setCreateTime(LocalDateTime.now())
                .setModifiedTime(LocalDateTime.now());
        userService.save(user);
        // 2、插入用户信息表
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId())
                .setNickname("用户" + user.getId())
                .setGender(null)
                .setProfilePhoto(null)
                .setWebsite(null)
                .setBio(null)
                .setFollowing(0)
                .setFollower(0)
                .setDeleted(false)
                .setVersion(0)
                .setCreateTime(LocalDateTime.now())
                .setModifiedTime(LocalDateTime.now());
        userInfoService.save(userInfo);
        // 3、插入用户权限表
        Role role = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getName, roleName));
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId())
                .setRoleId(role.getId())
                .setDeleted(false)
                .setVersion(0)
                .setCreateTime(LocalDateTime.now())
                .setModifiedTime(LocalDateTime.now());
        userRoleService.save(userRole);
    }

}
