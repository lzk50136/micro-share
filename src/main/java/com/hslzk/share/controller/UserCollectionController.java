package com.hslzk.share.controller;

import com.hslzk.share.jwt.JwtTokenUtil;
import com.hslzk.share.request.UserCollectionRequest;
import com.hslzk.share.service.UserCollectionService;
import com.hslzk.share.util.ApiResult;
import com.hslzk.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.hslzk.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/user_collection")
public class UserCollectionController {
    @Autowired
    private UserCollectionService userCollectionService;

    /**
     * 收藏贴子
     */
    @PostMapping(value = "/collect", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult collect(@RequestBody @Validated(UserCollectionRequest.Collect.class) UserCollectionRequest userCollectionRequest, @RequestHeader("Authorization") String authorization) {
        userCollectionService.collect(JwtTokenUtil.getUserId(authorization), userCollectionRequest);
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 获取用户收藏列表
     */
    @PostMapping(value = "/list_post_by_user_collection", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listPostByUserCollection(@RequestBody @Validated(UserCollectionRequest.ListCollect.class) UserCollectionRequest userCollectionRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(userCollectionService.listPostByUserCollection(userCollectionRequest, JwtTokenUtil.getUserId(authorization)));
    }

}
