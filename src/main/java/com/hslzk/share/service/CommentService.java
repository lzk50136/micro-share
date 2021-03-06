package com.hslzk.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hslzk.share.entity.Comment;
import com.hslzk.share.request.CommentRequest;
import com.hslzk.share.response.CommentResponse;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface CommentService extends IService<Comment> {

    Comment exist(Integer commentId);

    void comment(Integer userId, CommentRequest commentRequest);

    void delete(CommentRequest commentRequest, Integer userId);

    IPage<CommentResponse> listCommentByLikesNum(CommentRequest commentRequest);

    IPage<CommentResponse> listCommentByModifiedTime(CommentRequest commentRequest);

    /*List<CommentResponse> listCommentByAtMe(Integer userId, CommentRequest commentRequest);

    List<CommentResponse> listCommentByLikeMe(Integer userId, CommentRequest commentRequest);

    List<CommentResponse> listCommentByReplyToMe(Integer userId, CommentRequest commentRequest);*/

}
