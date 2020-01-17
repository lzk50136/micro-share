package com.hslzk.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hslzk.share.response.TopicResponse;
import com.hslzk.share.entity.Topic;
import com.hslzk.share.request.TopicFollowRequest;

/**
 * <p>
 * 话题表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface TopicService extends IService<Topic> {

    Topic exist(String topicName);

    IPage<TopicResponse> listTopicByUserFollow(TopicFollowRequest topicFollowRequest, Integer userId);

}
