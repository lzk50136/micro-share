package com.hslzk.share.controller;

import com.hslzk.share.jwt.JwtTokenUtil;
import com.hslzk.share.request.TopicFollowRequest;
import com.hslzk.share.service.TopicFollowService;
import com.hslzk.share.util.ApiResult;
import com.hslzk.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.hslzk.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/topic_follow")
public class TopicFollowController {
    @Autowired
    private TopicFollowService topicFollowService;

    /**
     * 关注/取消关注
     */
    @PostMapping(value = "/follow", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult follow(@RequestBody @Validated(TopicFollowRequest.Follow.class) TopicFollowRequest topicFollowRequest, @RequestHeader("Authorization") String authorization) {
        topicFollowService.follow(JwtTokenUtil.getUserId(authorization), topicFollowRequest);
        return ApiResultUtil.success("操作成功。");
    }

}
