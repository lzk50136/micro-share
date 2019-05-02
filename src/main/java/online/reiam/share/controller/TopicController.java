package online.reiam.share.controller;

import online.reiam.share.entity.Topic;
import online.reiam.share.request.TopicRequest;
import online.reiam.share.response.TopicResponse;
import online.reiam.share.service.TopicService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
