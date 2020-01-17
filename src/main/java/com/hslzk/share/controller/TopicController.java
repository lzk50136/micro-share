package com.hslzk.share.controller;

import com.hslzk.share.constants.Constants;
import com.hslzk.share.entity.Topic;
import com.hslzk.share.jwt.JwtTokenUtil;
import com.hslzk.share.request.TopicFollowRequest;
import com.hslzk.share.request.TopicRequest;
import com.hslzk.share.response.TopicResponse;
import com.hslzk.share.service.TopicService;
import com.hslzk.share.util.ApiResult;
import com.hslzk.share.util.ApiResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;

    /**
     * 获取话题信息
     */
    @PostMapping(value = "/get_topic", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult getTopic(@RequestBody @Validated(TopicRequest.GetTopic.class) TopicRequest topicRequest) {
        Topic topic = topicService.exist(topicRequest.getName());
        TopicResponse topicResponse = new TopicResponse();
        BeanUtils.copyProperties(topic, topicResponse);
        return ApiResultUtil.success(topicResponse);
    }

    /**
     * 获取用户关注的话题列表
     */
    @PostMapping(value = "/list_topic_by_user_follow", consumes = Constants.APPLICATION_JSON, produces = Constants.APPLICATION_JSON)
    public ApiResult listTopicByUserFollow(@RequestBody @Validated(TopicFollowRequest.ListTopicByUserFollow.class) TopicFollowRequest topicFollowRequest, @RequestHeader("Authorization") String authorization) {
        return ApiResultUtil.success(topicService.listTopicByUserFollow(topicFollowRequest, JwtTokenUtil.getUserId(authorization)));
    }

}
