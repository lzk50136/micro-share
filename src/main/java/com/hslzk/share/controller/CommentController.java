package com.hslzk.share.controller;

import com.hslzk.share.constants.Constants;
import com.hslzk.share.jwt.JwtTokenUtil;
import com.hslzk.share.request.CommentRequest;
import com.hslzk.share.service.CommentService;
import com.hslzk.share.util.ApiResult;
import com.hslzk.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 新增评论
     */
    @PostMapping(value = "/comment", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult comment(@RequestBody @Validated(CommentRequest.Comment.class) CommentRequest commentRequest, @RequestHeader("Authorization") String authorization) {
        commentService.comment(JwtTokenUtil.getUserId(authorization), commentRequest);
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 删除评论
     */
    @PostMapping(value = "/delete", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult delete(@RequestBody @Validated(CommentRequest.Delete.class) CommentRequest commentRequest, @RequestHeader("Authorization") String authorization) {
        commentService.delete(commentRequest, JwtTokenUtil.getUserId(authorization));
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 根据点赞数获取评论列表
     */
    @PostMapping(value = "/list_comment_by_like_num", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult listCommentByLikesNum(@RequestBody @Validated(CommentRequest.ListCommentByType.class) CommentRequest commentRequest) {
        return ApiResultUtil.success(commentService.listCommentByLikesNum(commentRequest));
    }

    /**
     * 根据评论时间获取评论列表
     */
    @PostMapping(value = "/list_comment_by_modified_time", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult listCommentByModifiedTime(@RequestBody @Validated(CommentRequest.ListCommentByType.class) CommentRequest commentRequest) {
        return ApiResultUtil.success(commentService.listCommentByModifiedTime(commentRequest));
    }

    /*@PostMapping(value = "/list_comment_by_at_me", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listCommentByAtMe(@RequestBody @Validated(CommentRequest.ListCommentByUserId.class) CommentRequest commentRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(commentService.listCommentByAtMe(JwtTokenUtil.getUserId(authorization), commentRequest));
    }

    @PostMapping(value = "/list_comment_by_like_me", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listCommentByLikeMe(@RequestBody @Validated(CommentRequest.ListCommentByUserId.class) CommentRequest commentRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(commentService.listCommentByLikeMe(JwtTokenUtil.getUserId(authorization), commentRequest));
    }

    @PostMapping(value = "/list_comment_by_reply_to_me", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listCommentByReplyToMe(@RequestBody @Validated(CommentRequest.ListCommentByUserId.class) CommentRequest commentRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(commentService.listCommentByReplyToMe(JwtTokenUtil.getUserId(authorization), commentRequest));
    }*/

}
