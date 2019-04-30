package online.reiam.share.controller;

import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.PostRequest;
import online.reiam.share.response.PostResponse;
import online.reiam.share.service.PostService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * 发布新贴子
     */
    @PostMapping(value = "/create_post", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult createPost(@RequestBody @Validated(PostRequest.AddPost.class) PostRequest postRequest, @RequestHeader("Authorization") String authorization) {
        PostResponse postResponse = postService.addPost(JwtTokenUtil.getUserId(authorization), postRequest);
        return ApiResultUtil.success(postResponse);
    }

    /**
     * 删除贴子
     */
    @PostMapping(value = "/delete_post", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult deletePost(@RequestBody @Validated(PostRequest.DeletePost.class) PostRequest postRequest, @RequestHeader("Authorization") String authorization) {
        postService.removePostById(JwtTokenUtil.getUserId(authorization), postRequest.getId());
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 获取某个用户的贴子列表
     */
    @PostMapping(value = "/list_post_by_nickname", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listPostByUserId(@RequestBody @Validated(PostRequest.ListPostByNickname.class) PostRequest postRequest) {
        return ApiResultUtil.success(postService.listPostByUserId(postRequest));
    }

    /**
     * 刷新关注的用户的贴子列表
     */
    @PostMapping(value = "/refresh_post_list", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult refreshPostList(@RequestBody @Validated(PostRequest.ListPostByFollowId.class) PostRequest postRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(postService.listPostByFollowId(JwtTokenUtil.getUserId(authorization), postRequest));
    }

}
