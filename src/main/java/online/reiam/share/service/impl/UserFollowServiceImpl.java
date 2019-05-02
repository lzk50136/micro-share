package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.UserFollow;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.UserFollowMapper;
import online.reiam.share.request.UserFollowRequest;
import online.reiam.share.response.UserFollowerResponse;
import online.reiam.share.response.UserFollowingResponse;
import online.reiam.share.service.UserFollowService;
import online.reiam.share.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户关注表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {
    @Autowired
    private UserInfoService userInfoService;
    @Resource
    private UserFollowMapper userFollowMapper;

    /**
     * 获取关注记录
     */
    @Override
    public UserFollow getFollow(Integer userId, Integer followId) {
        QueryWrapper<UserFollow> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserFollow::getUserId, userId)
                .eq(UserFollow::getFollowId, followId);
        return getOne(queryWrapper);
    }

    /**
     * 关注/取消关注
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void follow(Integer userId, Integer followId, UserFollowRequest userFollowRequest) {
        if (userId.equals(followId)) {
            throw new MicroShareException(10015, "不能自己关注自己。");
        }
        // 查找是否曾经关注过
        UserFollow userFollow = getFollow(userId, followId);
        // 如果提交的操作是关注
        if (userFollowRequest.getFollow()) {
            UserFollow userFollow2 = new UserFollow();
            // 如果没有关注过，插入新的关注记录
            if (userFollow == null) {
                userFollow2.setUserId(userId)
                        .setFollowId(followId)
                        .setFollow(true)
                        .setEachOther(false)
                        .setDeleted(false)
                        .setVersion(0)
                        .setCreateTime(LocalDateTime.now())
                        .setModifiedTime(LocalDateTime.now());
                save(userFollow2);
            } else {
                // 如果已经是关注状态，无需操作
                if (userFollow.getFollow()) {
                    throw new MicroShareException(10016, "已关注，请勿重复关注。");
                } else {
                    // 曾经有关注过，修改状态即可
                    userFollow2.setId(userFollow.getId())
                            .setFollow(true)
                            .setVersion(userFollow.getVersion())
                            .setModifiedTime(LocalDateTime.now());
                    updateById(userFollow2);
                }
            }
            // 查找对方是否有关注自己
            UserFollow userFollow3 = getFollow(followId, userId);
            // 如果有关注自己，则设置双方设置互粉
            if (userFollow3 != null && userFollow3.getFollow()) {
                // 修改自己互粉状态
                UserFollow userFollow4 = getById(userFollow2.getId());
                UserFollow userFollow5 = new UserFollow();
                userFollow5.setId(userFollow4.getId())
                        .setEachOther(true)
                        .setVersion(userFollow4.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                updateById(userFollow5);
                // 修改对方互粉状态
                UserFollow userFollow6 = new UserFollow();
                userFollow6.setId(userFollow3.getId())
                        .setEachOther(true)
                        .setVersion(userFollow3.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                updateById(userFollow6);
            }
            // 自己关注的人数+1
            UserInfo userInfo2 = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, userId));
            UserInfo userInfo3 = new UserInfo();
            userInfo3.setId(userInfo2.getId())
                    .setFollowing(userInfo2.getFollowing() + 1)
                    .setVersion(userInfo2.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            userInfoService.updateById(userInfo3);
            // 对方的粉丝人数+1
            UserInfo userInfo4 = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, followId));
            UserInfo userInfo5 = new UserInfo();
            userInfo5.setId(userInfo4.getId())
                    .setFollower(userInfo4.getFollower() + 1)
                    .setVersion(userInfo4.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            userInfoService.updateById(userInfo5);
            // 如果提交的操作是取消关注
        } else {
            // 如果从来没有关注过，无需操作
            if (userFollow == null) {
                throw new MicroShareException(10017, "没有关注过，何来取消关注呢？");
            } else {
                // 修改关注状态
                if (userFollow.getFollow()) {
                    UserFollow userFollow2 = new UserFollow();
                    userFollow2.setId(userFollow.getId())
                            .setFollow(false)
                            .setVersion(userFollow.getVersion())
                            .setModifiedTime(LocalDateTime.now());
                    updateById(userFollow2);
                } else {
                    // 已经是取消关注状态，无需操作
                    throw new MicroShareException(10018, "已取消关注，请勿重复取消。");
                }
            }
            // 如果是互粉状态，则修改双方互粉状态
            if (userFollow.getEachOther()) {
                // 修改自己互粉状态
                UserFollow userFollow2 = getById(userFollow.getId());
                UserFollow userFollow3 = new UserFollow();
                userFollow3.setId(userFollow.getId())
                        .setEachOther(false)
                        .setVersion(userFollow2.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                updateById(userFollow3);
                // 修改对方互粉状态
                UserFollow userFollow4 = getFollow(followId, userId);
                UserFollow userFollow5 = new UserFollow();
                userFollow5.setId(userFollow4.getId())
                        .setEachOther(false)
                        .setVersion(userFollow4.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                updateById(userFollow5);
            }
            // 自己关注的人数-1
            UserInfo userInfo2 = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, userId));
            UserInfo userInfo3 = new UserInfo();
            userInfo3.setId(userInfo2.getId())
                    .setFollowing(userInfo2.getFollowing() - 1)
                    .setVersion(userInfo2.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            userInfoService.updateById(userInfo3);
            // 对方的粉丝人数-1
            UserInfo userInfo4 = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, followId));
            UserInfo userInfo5 = new UserInfo();
            userInfo5.setId(userInfo4.getId())
                    .setFollower(userInfo4.getFollower() - 1)
                    .setVersion(userInfo4.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            userInfoService.updateById(userInfo5);
        }
    }

    /**
     * 查看用户粉丝的列表
     */
    @Override
    public IPage<UserFollowerResponse> listUserFollowerByUserId(UserFollowRequest userFollowRequest, Integer userId) {
        Page<UserFollowerResponse> page = new Page<>(userFollowRequest.getPageNum(), userFollowRequest.getPageSize());
        return userFollowMapper.selectUserFollowerListByUserId(page, userId);
    }

    /**
     * 查看用户关注的列表
     */
    @Override
    public IPage<UserFollowingResponse> listUserFollowingByUserId(UserFollowRequest userFollowRequest, Integer userId) {
        Page<UserFollowingResponse> page = new Page<>(userFollowRequest.getPageNum(), userFollowRequest.getPageSize());
        return userFollowMapper.selectUserFollowingListByUserId(page, userId);
    }

}
