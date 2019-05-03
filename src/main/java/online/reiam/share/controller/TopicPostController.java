package online.reiam.share.controller;

import online.reiam.share.request.TopicPostRequest;
import online.reiam.share.service.TopicPostService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@Controller
@RequestMapping("/topic_post")
public class TopicPostController {
    @Autowired
    private TopicPostService topicPostService;

    /**
     * 获取话题相关的贴子
     */
    @PostMapping(value = "/list_post_by_topic_name", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult listPostByTopicName(@RequestBody @Validated(TopicPostRequest.ListPostByTopicName.class) TopicPostRequest topicPostRequest) {
        return ApiResultUtil.success(topicPostService.listPostByTopicName(topicPostRequest));
    }

}
