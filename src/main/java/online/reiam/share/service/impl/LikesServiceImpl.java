package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.Comment;
import online.reiam.share.entity.Likes;
import online.reiam.share.entity.Post;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.LikesMapper;
import online.reiam.share.mapper.UserInfoMapper;
import online.reiam.share.request.LikesRequest;
import online.reiam.share.response.UserInfoResponse;
import online.reiam.share.service.CommentService;
import online.reiam.share.service.LikesService;
import online.reiam.share.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 点赞表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements LikesService {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Resource
    private LikesMapper likesMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 点赞/取消点赞
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likes(Integer userId, LikesRequest likesRequest) {
        if (likesRequest.getLikesType() == 0) {
            Post post = postService.exist(likesRequest.getTypeId());
            QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Likes::getUserId, userId)
                    .eq(Likes::getTypeId, likesRequest.getTypeId())
                    .eq(Likes::getLikesType, 0);
            Likes likes = getOne(queryWrapper);
            if (likesRequest.getLikes()) {
                Likes likes2 = new Likes();
                if (likes == null) {
                    likes2.setTypeId(post.getId())
                            .setUserId(userId)
                            .setLikes(true)
                            .setLikesType(likesRequest.getLikesType())
                            .setDeleted(false)
                            .setVersion(0)
                            .setCreateTime(LocalDateTime.now())
                            .setModifiedTime(LocalDateTime.now());
                    save(likes2);
                } else {
                    if (likes.getLikes()) {
                        throw new MicroShareException(10028, "已点赞，请勿重复点赞。");
                    } else {
                        likes2.setId(likes.getId())
                                .setLikes(true)
                                .setVersion(likes.getVersion())
                                .setModifiedTime(LocalDateTime.now());
                        updateById(likes2);
                    }
                }
                Post post2 = new Post();
                post2.setId(post.getId())
                        .setLikesNum(post.getLikesNum() + 1)
                        .setVersion(post.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                postService.updateById(post2);
            } else {
                if (likes == null) {
                    throw new MicroShareException(10029, "没有点赞过，何来取消点赞呢？");
                } else {
                    if (likes.getLikes()) {
                        Likes likes2 = new Likes();
                        likes2.setId(likes.getId())
                                .setLikes(false)
                                .setVersion(likes.getVersion())
                                .setModifiedTime(LocalDateTime.now());
                        updateById(likes2);
                    } else {
                        throw new MicroShareException(10030, "已取消点赞，请勿重复取消。");
                    }
                }
                Post post2 = new Post();
                post2.setId(post.getId())
                        .setLikesNum(post.getLikesNum() - 1)
                        .setVersion(post.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                postService.updateById(post2);
            }
        } else if (likesRequest.getLikesType() == 1) {
            Comment comment = commentService.exist(likesRequest.getTypeId());
            QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Likes::getUserId, userId)
                    .eq(Likes::getTypeId, likesRequest.getTypeId())
                    .eq(Likes::getLikesType, 1);
            Likes likes = getOne(queryWrapper);
            if (likesRequest.getLikes()) {
                Likes likes2 = new Likes();
                if (likes == null) {
                    likes2.setTypeId(comment.getId())
                            .setUserId(userId)
                            .setLikes(true)
                            .setLikesType(likesRequest.getLikesType())
                            .setDeleted(false)
                            .setVersion(0)
                            .setCreateTime(LocalDateTime.now())
                            .setModifiedTime(LocalDateTime.now());
                    save(likes2);
                } else {
                    if (likes.getLikes()) {
                        throw new MicroShareException(10028, "已点赞，请勿重复点赞。");
                    } else {
                        likes2.setId(likes.getId())
                                .setLikes(true)
                                .setVersion(likes.getVersion())
                                .setModifiedTime(LocalDateTime.now());
                        updateById(likes2);
                    }
                }
                Comment comment2 = new Comment();
                comment2.setId(comment.getId())
                        .setLikesNum(comment.getLikesNum() + 1)
                        .setVersion(comment.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                commentService.updateById(comment2);
            } else {
                if (likes == null) {
                    throw new MicroShareException(10029, "没有点赞过，何来取消点赞呢？");
                } else {
                    if (likes.getLikes()) {
                        Likes likes2 = new Likes();
                        likes2.setId(likes.getId())
                                .setLikes(false)
                                .setVersion(likes.getVersion())
                                .setModifiedTime(LocalDateTime.now());
                        updateById(likes2);
                    } else {
                        throw new MicroShareException(10030, "已取消点赞，请勿重复取消。");
                    }
                }
                Comment comment2 = new Comment();
                comment2.setId(comment.getId())
                        .setLikesNum(comment.getLikesNum() - 1)
                        .setVersion(comment.getVersion())
                        .setModifiedTime(LocalDateTime.now());
                commentService.updateById(comment2);
            }
        }
    }

    /**
     * 获取点赞的用户列表
     */
    @Override
    public IPage<UserInfoResponse> listUserInfoByLikes(LikesRequest likesRequest) {
        Page<Integer> page = new Page<>(likesRequest.getPageNum(), likesRequest.getPageSize());
        IPage<Integer> page2 = likesMapper.selectUserIdListByLikesAndType(page, likesRequest.getTypeId(), likesRequest.getLikesType());
        IPage<UserInfoResponse> page3 = new Page<>(likesRequest.getPageNum(), likesRequest.getPageSize());
        page3.setRecords(userInfoMapper.selectUserInfoListByUserIdList(page2.getRecords()))
                .setTotal(page2.getTotal()).setSize(page2.getSize()).setCurrent(page2.getCurrent()).setPages(page2.getPages());
        return page3;
    }

}
