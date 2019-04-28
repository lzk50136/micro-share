package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.reiam.share.entity.Role;
import online.reiam.share.entity.User;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.entity.UserRole;
import online.reiam.share.mapper.*;
import online.reiam.share.service.UserCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserCustomServiceImpl implements UserCustomService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCustomMapper userCustomMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 获取角色列表
     */
    @Override
    public List<String> findRoleNameListByUserId(Integer userId) {
        return userCustomMapper.selectRoleNameListByUserId(userId);
    }

    /**
     * 获取权限列表
     */
    @Override
    public List<String> findPermissionNameListByRoleName(String roleName) {
        return userCustomMapper.selectPermissionNameListByRoleName(roleName);
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
        userMapper.insert(user);
        // 2、插入用户信息表
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId())
                .setNickname("用户ID" + user.getId())
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
        userInfoMapper.insert(userInfo);
        // 3、插入用户权限表
        Role role = roleMapper.selectOne(new QueryWrapper<Role>().lambda().eq(Role::getName, roleName));
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId())
                .setRoleId(role.getId())
                .setDeleted(false)
                .setVersion(0)
                .setCreateTime(LocalDateTime.now())
                .setModifiedTime(LocalDateTime.now());
        userRoleMapper.insert(userRole);
    }

}
