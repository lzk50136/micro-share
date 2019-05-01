package online.reiam.share.controller;

import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.CommentRequest;
import online.reiam.share.service.CommentService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/comment", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult comment(@RequestBody @Validated(CommentRequest.Comment.class) CommentRequest commentRequest, @RequestHeader("Authorization") String authorization) {
        commentService.comment(JwtTokenUtil.getUserId(authorization), commentRequest);
        return ApiResultUtil.success("操作成功。");
    }

    @PostMapping(value = "/delete", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult delete(@RequestBody @Validated(CommentRequest.Delete.class) CommentRequest commentRequest, @RequestHeader("Authorization") String authorization) {
        commentService.delete(commentRequest, JwtTokenUtil.getUserId(authorization));
        return ApiResultUtil.success("操作成功。");
    }

    @PostMapping(value = "/list_comment_by_like_num", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listCommentByLikesNum(@RequestBody @Validated(CommentRequest.ListCommentByType.class) CommentRequest commentRequest) {
        return ApiResultUtil.success(commentService.listCommentByLikesNum(commentRequest));
    }

    @PostMapping(value = "/list_comment_by_modified_time", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
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
