package com.hslzk.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hslzk.share.response.CommentResponse;
import com.hslzk.share.service.CommentService;
import com.hslzk.share.service.PostService;
import com.hslzk.share.entity.AtMe;
import com.hslzk.share.entity.Comment;
import com.hslzk.share.entity.Post;
import com.hslzk.share.entity.UserInfo;
import com.hslzk.share.exception.MicroShareException;
import com.hslzk.share.mapper.CommentMapper;
import com.hslzk.share.request.CommentRequest;
import com.hslzk.share.service.AtMeService;
import com.hslzk.share.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 评论表 服务实现类
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
    @Autowired
    private AtMeService atMeService;
    @Resource
    private CommentMapper commentMapper;

    private Pattern pattern = Pattern.compile("@(?<name>[a-zA-Z\\d_\\u4e00-\\u9fa5]{1,14})\\s+");

    /**
     * 评论是否存在
     */
    @Override
    public Comment exist(Integer commentId) {
        Comment comment = getById(commentId);
        if (comment == null) {
            throw new MicroShareException(10032, "评论不存在。");
        }
        return comment;
    }

    /**
     * 新增评论
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void comment(Integer userId, CommentRequest commentRequest) {
        Integer commentId = null;
        // 如果评论类型为贴子评论
        if (commentRequest.getCommentType() == 0) {
            // 贴子是否存在及是否允许评论
            Post post = postService.exist(commentRequest.getTypeId());
            if (!post.getAllowComment()) {
                throw new MicroShareException(10031, "贴子不允许评论。");
            }
            // 插入评论记录
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
            // 更新贴子评论数量
            Post post2 = new Post();
            post2.setId(post.getId())
                    .setCommentNum(post.getCommentNum() + 1)
                    .setVersion(post.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            postService.updateById(post2);
            // 获取评论Id
            commentId = comment.getId();
            // 如果评论类型为回复评论
        } else if (commentRequest.getCommentType() == 1) {
            // 评论是否存在
            Comment comment = exist(commentRequest.getTypeId());
            // 插入评论记录
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
            // 更新回复评论数量
            Comment comment3 = new Comment();
            comment3.setId(comment.getId())
                    .setReplyNum(comment.getReplyNum() + 1)
                    .setVersion(comment.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            updateById(comment3);
            // 获取评论Id
            commentId = comment2.getId();
        }
        // 匹配评论中是否有艾特我的
        Matcher matcher = pattern.matcher(commentRequest.getContent());
        while (matcher.find()) {
            String name = matcher.group("name");
            UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getNickname, name));
            // 如果艾特的昵称是存在的话插入艾特表
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

    /**
     * 删除评论
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(CommentRequest commentRequest, Integer userId) {
        // 查找评论是否存在及是否属于自己的评论
        Comment comment = exist(commentRequest.getId());
        if (!comment.getUserId().equals(userId)) {
            throw new MicroShareException(10033, "只能删除自己的评论。");
        }
        // 删除评论
        removeById(commentRequest.getId());
        // 如果评论类型是贴子评论，更新贴子评论数
        if (commentRequest.getCommentType() == 0) {
            Post post = postService.getById(comment.getTypeId());
            Post post2 = new Post();
            post2.setId(post.getId())
                    .setCommentNum(post.getCommentNum() - 1)
                    .setVersion(post.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            postService.updateById(post2);
            // 如果评论类型是回复评论，更新回复评论数
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

    /**
     * 根据点赞数获取评论列表
     */
    @Override
    public IPage<CommentResponse> listCommentByLikesNum(CommentRequest commentRequest) {
        Page<CommentResponse> page = new Page<>(commentRequest.getPageNum(), commentRequest.getPageSize());
        return commentMapper.selectCommentListByLikesNum(page, commentRequest.getTypeId(), commentRequest.getCommentType());
    }

    /**
     * 根据评论时间获取评论列表
     */
    @Override
    public IPage<CommentResponse> listCommentByModifiedTime(CommentRequest commentRequest) {
        Page<CommentResponse> page = new Page<>(commentRequest.getPageNum(), commentRequest.getPageSize());
        return commentMapper.selectCommentListByModifiedTime(page, commentRequest.getTypeId(), commentRequest.getCommentType());
    }

    /*@Override
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
