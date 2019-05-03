package online.reiam.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.TopicFollow;
import online.reiam.share.request.TopicFollowRequest;

/**
 * <p>
 * 话题关注表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface TopicFollowService extends IService<TopicFollow> {

    void follow(Integer userId, TopicFollowRequest topicFollowRequest);

}
