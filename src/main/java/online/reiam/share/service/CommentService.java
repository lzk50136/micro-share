package online.reiam.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.Comment;
import online.reiam.share.request.CommentRequest;

/**
 * <p>
 * 贴子评论表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface CommentService extends IService<Comment> {

    Comment commentExist(Integer commentId);

    void comment(Integer userId, CommentRequest commentRequest);

    void delete(CommentRequest commentRequest, Integer userId);

    void hasPermission(CommentRequest commentRequest);

    /*IPage<CommentResponse> listCommentByLikesNum(CommentRequest commentRequest);

    IPage<CommentResponse> listCommentByModifiedTime(CommentRequest commentRequest);

    List<CommentResponse> listCommentByAtMe(Integer userId, CommentRequest commentRequest);

    List<CommentResponse> listCommentByLikeMe(Integer userId, CommentRequest commentRequest);

    List<CommentResponse> listCommentByReplyToMe(Integer userId, CommentRequest commentRequest);*/

}
