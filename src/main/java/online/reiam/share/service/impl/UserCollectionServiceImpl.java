package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.Post;
import online.reiam.share.entity.UserCollection;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.PostMapper;
import online.reiam.share.mapper.UserCollectionMapper;
import online.reiam.share.request.UserCollectionRequest;
import online.reiam.share.response.PostResponse;
import online.reiam.share.service.PostService;
import online.reiam.share.service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户收藏表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements UserCollectionService {
    @Autowired
    private PostService postService;
    @Resource
    private UserCollectionMapper userCollectionMapper;
    @Resource
    private PostMapper postMapper;

    @Override
    public void collect(Integer userId, UserCollectionRequest userCollectionRequest) {
        Post post = postService.exist(userCollectionRequest.getPostId());
        QueryWrapper<UserCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserCollection::getUserId, userId)
                .eq(UserCollection::getPostId, userCollectionRequest.getPostId());
        UserCollection userCollection = getOne(queryWrapper);
        if (userCollectionRequest.getCollect()) {
            if (userCollection != null) {
                throw new MicroShareException(10035, "已经收藏过，请勿重复收藏。");
            }
            UserCollection userCollection2 = new UserCollection();
            userCollection2.setUserId(userId)
                    .setPostId(userCollectionRequest.getPostId())
                    .setDeleted(false)
                    .setVersion(0)
                    .setCreateTime(LocalDateTime.now())
                    .setModifiedTime(LocalDateTime.now());
            if (!save(userCollection2)) {
                throw new MicroShareException(10001, "操作失败。");
            }
        } else {
            if (userCollection == null) {
                throw new MicroShareException(10036, "没有收藏过，不能取消。");
            }
            if (!removeById(userCollection.getId())) {
                throw new MicroShareException(10001, "操作失败。");
            }
        }
    }

    @Override
    public IPage<PostResponse> listPostByUserCollection(UserCollectionRequest userCollectionRequest, Integer userId) {
        Page<Integer> page = new Page<>(userCollectionRequest.getPageNum(), userCollectionRequest.getPageSize());
        IPage<Integer> page2 = userCollectionMapper.selectPostIdListByUserId(page, userId);
        IPage<PostResponse> page3 = new Page<>(userCollectionRequest.getPageNum(), userCollectionRequest.getPageSize());
        page3.setRecords(postMapper.selectPostListByIdList(page2.getRecords()))
                .setPages(page2.getPages())
                .setTotal(page2.getTotal())
                .setSize(page2.getSize())
                .setCurrent(page2.getCurrent());
        return page3;
    }

}
