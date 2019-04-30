package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.AtMe;
import online.reiam.share.entity.Comment;
import online.reiam.share.entity.Post;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.AtMeMapper;
import online.reiam.share.mapper.CommentMapper;
import online.reiam.share.request.CommentRequest;
import online.reiam.share.service.AtMeService;
import online.reiam.share.service.CommentService;
import online.reiam.share.service.PostService;
import online.reiam.share.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 贴子评论表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private PostService postService;
    @Autowired
    private UserInfoService userInfoService;
    @Resource
    private AtMeMapper atMeMapper;
    @Autowired
    private AtMeService atMeService;

    private Pattern pattern = Pattern.compile("@(?<name>[a-zA-Z\\d_\\u4e00-\\u9fa5]{1,14})\\s+");

    @Override
    public Comment commentNotExist(Integer commentId) {
        Comment comment = getById(commentId);
        if (comment == null) {
            throw new MicroShareException(10024, "评论不存在。");
        }
        return comment;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void comment(Integer userId, CommentRequest commentRequest) {
        Integer commentId = null;
        if (commentRequest.getCommentType() == 0) {
            Post post = postService.getById(commentRequest.getTypeId());
            if (post == null) {
                throw new MicroShareException(10024, "贴子不存在。");
            }
            if (!post.getAllowComment()) {
                throw new MicroShareException(10031, "贴子不允许评论。");
            }
            Comment comment = new Comment();
            comment.setTypeId(commentRequest.getTypeId())
                    .setUserId(userId)
                    .setContent(commentRequest.getContent())
                    .setLikesNum(0)
                    .setReplyNum(0)
                    .setReplyTo(post.getUserId())
                    .setCommentType(commentRequest.getCommentType())
                    .setDeleted(false)
                    .setVersion(0)
                    .setCreateTime(LocalDateTime.now())
                    .setModifiedTime(LocalDateTime.now());
            save(comment);
            commentId = comment.getId();
            Post post2 = new Post();
            post2.setId(post.getId())
                    .setCommentNum(post.getCommentNum() + 1)
                    .setVersion(post.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            postService.updateById(post2);
        } else if (commentRequest.getCommentType() == 1) {
            Comment comment = getById(commentRequest.getTypeId());
            if (comment == null) {
                throw new MicroShareException(10032, "评论不存在。");
            }
            Comment comment2 = new Comment();
            comment2.setTypeId(commentRequest.getTypeId())
                    .setUserId(userId)
                    .setContent(commentRequest.getContent())
                    .setLikesNum(0)
                    .setReplyNum(0)
                    .setReplyTo(comment.getUserId())
                    .setCommentType(commentRequest.getCommentType())
                    .setDeleted(false)
                    .setVersion(0)
                    .setModifiedTime(LocalDateTime.now())
                    .setCreateTime(LocalDateTime.now());
            save(comment2);
            commentId = comment2.getId();
            Comment comment3 = new Comment();
            comment3.setId(comment.getId())
                    .setReplyNum(comment.getReplyNum() + 1)
                    .setVersion(comment.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            updateById(comment3);
        }
        Matcher matcher = pattern.matcher(commentRequest.getContent());
        while (matcher.find()) {
            String name = matcher.group("name");
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(UserInfo::getNickname, name);
            UserInfo userInfo = userInfoService.getOne(queryWrapper);
            if (userInfo != null) {
                AtMe atMe = new AtMe();
                atMe.setTypeId(commentId)
                        .setUserId(userInfo.getUserId())
                        .setAtMeType(1)
                        .setDeleted(false)
                        .setVersion(0)
                        .setCreateTime(LocalDateTime.now())
                        .setModifiedTime(LocalDateTime.now());
                atMeService.save(atMe);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(CommentRequest commentRequest, Integer userId) {
        Comment comment = getById(commentRequest.getId());
        if (comment == null) {
            throw new MicroShareException(10032, "评论不存在。");
        }
        if (!comment.getUserId().equals(userId)) {
            throw new MicroShareException(10033, "只能删除自己的评论。");
        }
        removeById(commentRequest.getId());
        if (commentRequest.getCommentType() == 0) {
            Post post = postService.getById(comment.getTypeId());
            Post post2 = new Post();
            post2.setId(post.getId())
                    .setCommentNum(post.getCommentNum() - 1)
                    .setVersion(post.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            postService.updateById(post2);
        } else if (commentRequest.getCommentType() == 1) {
            Comment comment2 = getById(comment.getTypeId());
            Comment comment3 = new Comment();
            comment3.setId(comment2.getId())
                    .setReplyNum(comment2.getReplyNum() - 1)
                    .setVersion(comment2.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            updateById(comment3);
        }
    }

    @Override
    public void hasPermission(CommentRequest commentRequest) {
        if (commentRequest.getCommentType() == 0) {
            Post post = postService.getById(commentRequest.getTypeId());
            if (post == null) {
                throw new MicroShareException(10024, "贴子不存在。");
            }
            if (!post.getAllowComment()) {
                throw new MicroShareException(10031, "贴子不允许评论。");
            }
        } else if (commentRequest.getCommentType() == 1) {
            Comment comment = getById(commentRequest.getTypeId());
            if (comment == null) {
                throw new MicroShareException(10032, "评论不存在。");
            }
        }
    }

    /*@Override
    public IPage<CommentResponse> listCommentByLikesNum(CommentRequest commentRequest) {
        hasPermission(commentRequest);
        Page<CommentResponse> page = new Page<>(commentRequest.getPageNum(), commentRequest.getPageSize());
        return commentMapper.selectCommentByLikesNum(page, commentRequest.getTypeId(), commentRequest.getCommentType());
    }

    @Override
    public IPage<CommentResponse> listCommentByModifiedTime(CommentRequest commentRequest) {
        hasPermission(commentRequest);
        Page<CommentResponse> page = new Page<>(commentRequest.getPageNum(), commentRequest.getPageSize());
        return commentMapper.selectCommentByModifiedTime(page, commentRequest.getTypeId(), commentRequest.getCommentType());
    }

    @Override
    public List<CommentResponse> listCommentByAtMe(Integer userId, CommentRequest commentRequest) {
        Page<Integer> page = new Page<>(commentRequest.getPageNum(), commentRequest.getPageSize());
        List<Integer> integerList = commentAtMapper.selectCommentIdByUserId(page, userId).getRecords();
        return commentMapper.selectCommentByIdList(integerList);
    }

    @Override
    public List<CommentResponse> listCommentByLikeMe(Integer userId, CommentRequest commentRequest) {
        Page<Integer> page = new Page<>(commentRequest.getPageNum(), commentRequest.getPageSize());
        //List<Integer> integerList = commentLikesMapper.selectCommentIdByUserId(page, userId).getRecords();
        //return commentMapper.selectCommentByIdList(integerList);
        return null;
    }

    @Override
    public List<CommentResponse> listCommentByReplyToMe(Integer userId, CommentRequest commentRequest) {
        Page<Integer> page = new Page<>(commentRequest.getPageNum(), commentRequest.getPageSize());
        List<Integer> integerList = commentMapper.selectCommentIdByReplyTo(page, userId).getRecords();
        return commentMapper.selectCommentByIdList(integerList);
    }*/

}
