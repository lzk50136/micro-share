package com.hslzk.share.controller;

import com.hslzk.share.jwt.JwtTokenUtil;
import com.hslzk.share.request.LikesRequest;
import com.hslzk.share.service.LikesService;
import com.hslzk.share.util.ApiResult;
import com.hslzk.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.hslzk.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/likes")
public class LikesController {
    @Autowired
    private LikesService likesService;

    /**
     * 点赞/取消点赞
     */
    @PostMapping(value = "/likes", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult likes(@RequestBody @Validated(LikesRequest.Likes.class) LikesRequest likesRequest, @RequestHeader("Authorization") String authorization) {
        likesService.likes(JwtTokenUtil.getUserId(authorization), likesRequest);
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 获取点赞的用户列表
     */
    @PostMapping(value = "/list_user_info_by_likes", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listUserInfoByLikes(@RequestBody @Validated(LikesRequest.ListUserInfoByLikes.class) LikesRequest likesRequest) {
        return ApiResultUtil.success(likesService.listUserInfoByLikes(likesRequest));
    }

}
