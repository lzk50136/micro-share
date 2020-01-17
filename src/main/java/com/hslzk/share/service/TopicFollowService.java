package com.hslzk.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hslzk.share.entity.TopicFollow;
import com.hslzk.share.request.TopicFollowRequest;

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
