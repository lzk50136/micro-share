package online.reiam.share.controller;

import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.UserCollectionRequest;
import online.reiam.share.service.UserCollectionService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/user_collection")
public class UserCollectionController {
    @Autowired
    private UserCollectionService userCollectionService;

    @PostMapping(value = "/collect", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult collect(@RequestBody @Validated(UserCollectionRequest.Collect.class) UserCollectionRequest userCollectionRequest, @RequestHeader("Authorization") String authorization) {
        userCollectionService.collect(JwtTokenUtil.getUserId(authorization), userCollectionRequest);
        return ApiResultUtil.success("操作成功。");
    }

    @PostMapping(value = "/list_post_by_user_collection", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listPostByUserCollection(@RequestBody @Validated(UserCollectionRequest.ListCollect.class) UserCollectionRequest userCollectionRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(userCollectionService.listPostByUserCollection(userCollectionRequest, JwtTokenUtil.getUserId(authorization)));
    }

}
