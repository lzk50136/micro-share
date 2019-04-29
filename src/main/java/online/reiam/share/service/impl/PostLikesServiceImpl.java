package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.Post;
import online.reiam.share.entity.PostLikes;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.PostLikesMapper;
import online.reiam.share.request.PostLikesRequest;
import online.reiam.share.response.PostLikesResponse;
import online.reiam.share.service.PostLikesService;
import online.reiam.share.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 贴子点赞表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class PostLikesServiceImpl extends ServiceImpl<PostLikesMapper, PostLikes> implements PostLikesService {
    @Autowired
    private PostService postService;
    @Resource
    private PostLikesMapper postLikesMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likes(Integer userId, PostLikesRequest postLikesRequest, Post post) {
        QueryWrapper<PostLikes> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PostLikes::getUserId, userId)
                .eq(PostLikes::getPostId, postLikesRequest.getPostId());
        PostLikes postLikes = getOne(queryWrapper);
        if (postLikesRequest.getLikes()) {
            PostLikes postLikes2 = new PostLikes();
            if (postLikes == null) {
                postLikes2.setPostId(postLikesRequest.getPostId())
                        .setUserId(userId)
                        .setLikes(true)
                        .setDeleted(false)
                        .setVersion(0)
                        .setCreateTime(LocalDateTime.now())
                        .setModifiedTime(LocalDateTime.now());
                save(postLikes2);
            } else {
                if (postLikes.getLikes()) {
                    throw new MicroShareException(10028, "已点赞，请勿重复点赞。");
                } else {
                    postLikes2.setId(postLikes.getId())
                            .setLikes(true)
                            .setVersion(postLikes.getVersion())
                            .setModifiedTime(LocalDateTime.now());
                    updateById(postLikes2);
                }
            }
            Post post2 = new Post();
            post2.setId(post.getId())
                    .setLikesNum(post.getLikesNum() + 1)
                    .setVersion(post.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            postService.updateById(post2);
        } else {
            if (postLikes == null) {
                throw new MicroShareException(10029, "没有点赞过，何来取消点赞呢？");
            } else {
                if (postLikes.getLikes()) {
                    PostLikes postLikes2 = new PostLikes();
                    postLikes2.setId(postLikes.getId())
                            .setLikes(false)
                            .setVersion(postLikes.getVersion())
                            .setModifiedTime(LocalDateTime.now());
                    updateById(postLikes2);
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
    }

    @Override
    public IPage<PostLikesResponse> selectPostLikes(PostLikesRequest postLikesRequest) {
        Page<PostLikesResponse> page = new Page<>(postLikesRequest.getPageNum(), postLikesRequest.getPageSize());
        return postLikesMapper.selectPostLikesListByPostId(page, postLikesRequest.getPostId());
    }

}
