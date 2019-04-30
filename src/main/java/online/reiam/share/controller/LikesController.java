package online.reiam.share.controller;

import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.LikesRequest;
import online.reiam.share.request.PostLikesRequest;
import online.reiam.share.service.LikesService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/likes")
public class LikesController {
    @Autowired
    private LikesService likesService;

    /**
     * 点赞/取消点赞
     */
    @PostMapping(value = "/likes", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult likes(@RequestBody @Validated(PostLikesRequest.Likes.class) LikesRequest likesRequest, @RequestHeader("Authorization") String authorization) {
        likesService.likes(JwtTokenUtil.getUserId(authorization), likesRequest);
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 获取点赞的用户列表
     */
    @PostMapping(value = "/list_user_info_by_likes", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listUserInfoByLikes(@RequestBody @Validated(LikesRequest.ListLikesByType.class) LikesRequest likesRequest) {
        return ApiResultUtil.success(likesService.listUserInfoByLikesAndType(likesRequest));
    }

}
