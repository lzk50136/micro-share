package com.hslzk.share.controller;

import com.hslzk.share.constants.Constants;
import com.hslzk.share.jwt.JwtTokenUtil;
import com.hslzk.share.request.PostRequest;
import com.hslzk.share.response.PostResponse;
import com.hslzk.share.service.PostService;
import com.hslzk.share.util.ApiResult;
import com.hslzk.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * 发布新贴子
     */
    @PostMapping(value = "/create", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult create(@RequestBody @Validated(PostRequest.Create.class) PostRequest postRequest, @RequestHeader("Authorization") String authorization) {
        PostResponse postResponse = postService.create(JwtTokenUtil.getUserId(authorization), postRequest);
        return ApiResultUtil.success(postResponse);
    }

    /**
     * 删除贴子
     */
    @PostMapping(value = "/delete", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult delete(@RequestBody @Validated(PostRequest.Delete.class) PostRequest postRequest, @RequestHeader("Authorization") String authorization) {
        postService.delete(JwtTokenUtil.getUserId(authorization), postRequest.getId());
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 获取某个用户的贴子列表
     */
    @PostMapping(value = "/list_post_by_nickname", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult listPostByUserId(@RequestBody @Validated(PostRequest.ListPostByNickname.class) PostRequest postRequest) {
        return ApiResultUtil.success(postService.listPostByUserId(postRequest));
    }

    /**
     * 刷新关注的用户的贴子列表
     */
    @PostMapping(value = "/refresh_post_list", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult refreshPostList(@RequestBody @Validated(PostRequest.ListPostByFollowId.class) PostRequest postRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(postService.listPostByFollowId(JwtTokenUtil.getUserId(authorization), postRequest));
    }

}
