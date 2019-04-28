package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.entity.UserFollow;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.UserFollowCustomMapper;
import online.reiam.share.mapper.UserFollowMapper;
import online.reiam.share.mapper.UserInfoMapper;
import online.reiam.share.request.UserFollowRequest;
import online.reiam.share.response.UserFollowerResponse;
import online.reiam.share.response.UserFollowingResponse;
import online.reiam.share.service.UserFollowCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserFollowCustomServiceImpl implements UserFollowCustomService {
    @Autowired
    private UserFollowCustomMapper userFollowCustomMapper;
    @Autowired
    private UserFollowMapper userFollowMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 关注服务
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void follow(Integer userId, UserInfo userInfo, UserFollowRequest userFollowRequest) {
        // 查找是否曾经关注过
        QueryWrapper<UserFollow> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserFollow::getUserId, userId)
                .eq(UserFollow::getFollowId, userInfo.getUserId());
        UserFollow userFollow = userFollowMapper.selectOne(queryWrapper);
        // 如果提交的操作是关注
        if (userFollowRequest.getFollow()) {
            UserFollow userFollow2 = new UserFollow();
            // 如果没有关注过，插入新的关注记录
            if (userFollow == null) {
                userFollow2.setUserId(userId)
                        .setFollowId(userInfo.getUserId())
                        .setFollow(true)
                        .setEachOther(false)
                        .setDeleted(false)
                        .setVersion(0)
                        .setCreateTime(LocalDateTime.now())
                        .setModifiedTime(LocalDateTime.now());
                userFollowMapper.insert(userFollow2);
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
                    userFollowMapper.updateById(userFollow2);
                }
            }
            // 查找对方是否有关注自己
            QueryWrapper<UserFollow> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(UserFollow::getUserId, userInfo.getUserId())
                    .eq(UserFollow::getFollowId, userId);
            UserFollow userFollow3 = userFollowMapper.selectOne(queryWrapper2);
            // 如果有关注自己，则设置双方设置互粉
            if (userFollow3 != null && userFollow3.getFollow()) {
                // 修改自己互粉状态
                UserFollow userFollow4 = userFollowMapper.selectById(userFollow2.getId());
                UserFollow userFollow5 = new UserFollow();
                userFollow5.setId(userFollow4.getId())
                        .setEachOther(true)
                        .setVersion(userFollow4.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                userFollowMapper.updateById(userFollow5);
                // 修改对方互粉状态
                UserFollow userFollow6 = new UserFollow();
                userFollow6.setId(userFollow3.getId())
                        .setEachOther(true)
                        .setVersion(userFollow3.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                userFollowMapper.updateById(userFollow6);
            }
            // 自己关注的人数+1
            UserInfo userInfo2 = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, userId));
            UserInfo userInfo3 = new UserInfo();
            userInfo3.setId(userInfo2.getId())
                    .setFollowing(userInfo2.getFollowing() + 1)
                    .setVersion(userInfo2.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            userInfoMapper.updateById(userInfo3);
            // 对方的粉丝人数+1
            UserInfo userInfo4 = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, userInfo.getUserId()));
            UserInfo userInfo5 = new UserInfo();
            userInfo5.setId(userInfo4.getId())
                    .setFollower(userInfo4.getFollower() + 1)
                    .setVersion(userInfo4.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            userInfoMapper.updateById(userInfo5);
        } else {
            // 如果提交的操作是取消关注
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
                    userFollowMapper.updateById(userFollow2);
                } else {
                    // 已经是取消关注状态，无需操作
                    throw new MicroShareException(10018, "已取消关注，请勿重复取消。");
                }
            }
            // 如果是互粉状态，则修改双方互粉状态
            if (userFollow.getEachOther()) {
                // 修改自己互粉状态
                UserFollow userFollow2 = userFollowMapper.selectById(userFollow.getId());
                UserFollow userFollow3 = new UserFollow();
                userFollow3.setId(userFollow.getId())
                        .setEachOther(false)
                        .setVersion(userFollow2.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                userFollowMapper.updateById(userFollow3);
                // 修改对方互粉状态
                QueryWrapper<UserFollow> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.lambda().eq(UserFollow::getUserId, userInfo.getUserId())
                        .eq(UserFollow::getFollowId, userId);
                UserFollow userFollow4 = userFollowMapper.selectOne(queryWrapper2);
                UserFollow userFollow5 = new UserFollow();
                userFollow5.setId(userFollow4.getId())
                        .setEachOther(false)
                        .setVersion(userFollow4.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                userFollowMapper.updateById(userFollow5);
            }
            // 自己关注的人数-1
            UserInfo userInfo2 = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, userId));
            UserInfo userInfo3 = new UserInfo();
            userInfo3.setId(userInfo2.getId())
                    .setFollowing(userInfo2.getFollowing() - 1)
                    .setVersion(userInfo2.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            userInfoMapper.updateById(userInfo3);
            // 对方的粉丝人数-1
            UserInfo userInfo4 = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, userInfo.getUserId()));
            UserInfo userInfo5 = new UserInfo();
            userInfo5.setId(userInfo4.getId())
                    .setFollower(userInfo4.getFollower() - 1)
                    .setVersion(userInfo4.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            userInfoMapper.updateById(userInfo5);
        }
    }

    /**
     * 查看用户粉丝的列表
     */
    @Override
    public IPage<UserFollowerResponse> findUserFollowerListByUserId(UserFollowRequest userFollowRequest, Integer userId) {
        Page<UserFollowerResponse> page = new Page<>(userFollowRequest.getPageNum(), userFollowRequest.getPageSize());
        return userFollowCustomMapper.selectUserFollowerListByUserId(page, userId);
    }

    /**
     * 查看用户关注的列表
     */
    @Override
    public IPage<UserFollowingResponse> findUserFollowingListByUserId(UserFollowRequest userFollowRequest, Integer userId) {
        Page<UserFollowingResponse> page = new Page<>(userFollowRequest.getPageNum(), userFollowRequest.getPageSize());
        return userFollowCustomMapper.selectUserFollowingListByUserId(page, userId);
    }

}
