package online.reiam.share.controller;

import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.TopicFollowRequest;
import online.reiam.share.service.TopicFollowService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/topic_follow")
public class TopicFollowController {
    @Autowired
    private TopicFollowService topicFollowService;

    @PostMapping(value = "/follow", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult follow(@RequestBody @Validated(TopicFollowRequest.Follow.class) TopicFollowRequest topicFollowRequest, @RequestHeader("Authorization") String authorization) {
        topicFollowService.follow(JwtTokenUtil.getUserId(authorization), topicFollowRequest);
        return ApiResultUtil.success("操作成功。");
    }

}
