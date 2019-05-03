package online.reiam.share.controller;

import online.reiam.share.entity.Topic;
import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.TopicFollowRequest;
import online.reiam.share.request.TopicRequest;
import online.reiam.share.response.TopicResponse;
import online.reiam.share.service.TopicService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @PostMapping(value = "/get_topic", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult getTopic(@RequestBody @Validated(TopicRequest.GetTopic.class) TopicRequest topicRequest) {
        Topic topic = topicService.exist(topicRequest.getName());
        TopicResponse topicResponse = new TopicResponse();
        BeanUtils.copyProperties(topic, topicResponse);
        return ApiResultUtil.success(topicResponse);
    }

    @PostMapping(value = "/list_topic_by_user_follow", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listTopicByUserFollow(@RequestBody @Validated(TopicFollowRequest.ListTopicByUserFollow.class) TopicFollowRequest topicFollowRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(topicService.listTopicByUserFollow(topicFollowRequest, JwtTokenUtil.getUserId(authorization)));
    }

}
