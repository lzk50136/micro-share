package online.reiam.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.Topic;
import online.reiam.share.request.TopicFollowRequest;
import online.reiam.share.response.TopicResponse;

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
