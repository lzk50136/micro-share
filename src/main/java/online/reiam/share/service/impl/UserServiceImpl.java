package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.Role;
import online.reiam.share.entity.User;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.entity.UserRole;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.UserMapper;
import online.reiam.share.service.RoleService;
import online.reiam.share.service.UserInfoService;
import online.reiam.share.service.UserRoleService;
import online.reiam.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 获取角色列表
     */
    @Override
    public List<String> listRoleNameByUserId(Integer userId) {
        return userMapper.selectRoleNameListByUserId(userId);
    }

    /**
     * 获取权限列表
     */
    @Override
    public List<String> listPermissionNameByRoleName(String roleName) {
        return userMapper.selectPermissionNameListByRoleName(roleName);
    }

    /**
     * 用户是否存在
     */
    @Override
    public User userExist(String username) {
        User user = getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
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
        save(user);
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
