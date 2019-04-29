package online.reiam.share.controller;

import online.reiam.share.entity.UserInfo;
import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.PostRequest;
import online.reiam.share.response.PostResponse;
import online.reiam.share.service.PostCustomService;
import online.reiam.share.service.UserInfoCustomService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/user_post")
public class UserPostController {
    @Autowired
    private UserInfoCustomService userInfoCustomService;
    @Autowired
    private PostCustomService postCustomService;

    /**
     * 发布新贴子
     */
    @PostMapping(value = "/create_post", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult createPost(@RequestBody @Validated(PostRequest.AddPost.class) PostRequest postRequest, @RequestHeader("Authorization") String authorization) {
        PostResponse postResponse = postCustomService.addPost(JwtTokenUtil.getUserId(authorization), postRequest);
        return ApiResultUtil.success(postResponse);
    }

    /**
     * 删除贴子
     */
    @PostMapping(value = "/delete_post", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult deletePost(@RequestBody @Validated(PostRequest.DeletePost.class) PostRequest postRequest, @RequestHeader("Authorization") String authorization) {
        postCustomService.removePostById(JwtTokenUtil.getUserId(authorization), postRequest.getId());
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 获取某个用户的贴子列表
     */
    @PostMapping(value = "/list_post_by_nickname", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listPostByUserId(@RequestBody @Validated(PostRequest.ListPostByNickname.class) PostRequest postRequest) {
        UserInfo userInfo = userInfoCustomService.userExist(postRequest.getNickname());
        return ApiResultUtil.success(postCustomService.listPostByUserId(postRequest, userInfo));
    }

    /**
     * 获取某个话题的贴子列表
     */
    @PostMapping(value = "/list_post_by_topic_name", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listPostByTopicName(@RequestBody @Validated(PostRequest.ListPostByTopicName.class) PostRequest postRequest) {
        return ApiResultUtil.success(postCustomService.listPostByTopicId(postRequest));
    }

    /**
     * 刷新关注的用户的贴子列表
     */
    @PostMapping(value = "/refresh_post_list", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult refreshPostList(@RequestBody @Validated(PostRequest.ListPostByFollowId.class) PostRequest postRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(postCustomService.listPostByFollowId(JwtTokenUtil.getUserId(authorization), postRequest));
    }

    /**
     * 获取艾特我的贴子列表
     */
    @PostMapping(value = "/list_post_by_at_me", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listPostByAtMe(@RequestBody @Validated(PostRequest.ListPostByUserId.class) PostRequest postRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(postCustomService.listPostByAtMe(postRequest, JwtTokenUtil.getUserId(authorization)));
    }

    @PostMapping(value = "/list_post_by_like_me", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listPostByLikeMe(@RequestBody @Validated(PostRequest.ListPostByUserId.class) PostRequest postRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(postCustomService.listPostByLikeMe(postRequest, JwtTokenUtil.getUserId(authorization)));
    }

}
