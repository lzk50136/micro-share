package online.reiam.share.controller;

import online.reiam.share.entity.Post;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.PostLikesRequest;
import online.reiam.share.service.PostLikesService;
import online.reiam.share.service.PostService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/post_likes")
public class PostLikesController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostLikesService postLikesService;

    @PostMapping(value = "/likes", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult likes(@RequestBody @Validated(PostLikesRequest.Likes.class) PostLikesRequest postLikesRequest, @RequestHeader("Authorization") String authorization) {
        Post post = postService.getById(postLikesRequest.getPostId());
        if (post == null) {
            throw new MicroShareException(10024, "贴子不存在。");
        }
        postLikesService.likes(JwtTokenUtil.getUserId(authorization), postLikesRequest, post);
        return ApiResultUtil.success("操作成功。");
    }

    @PostMapping(value = "/list_likes_by_post_id", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listLikesByPostId(@RequestBody @Validated(PostLikesRequest.ListLikesByPostId.class) PostLikesRequest postLikesRequest) {
        Post post = postService.getById(postLikesRequest.getPostId());
        if (post == null) {
            throw new MicroShareException(10024, "贴子不存在。");
        }
        return ApiResultUtil.success(postLikesService.selectPostLikes(postLikesRequest));
    }

    @PostMapping(value = "/list_likes_by_user_id", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listLikesByUserId(@RequestBody @Validated(PostLikesRequest.ListLikesByUserId.class) PostLikesRequest postLikesRequest) {
        return null;
    }

}
