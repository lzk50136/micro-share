package com.hslzk.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hslzk.share.entity.TopicPost;
import com.hslzk.share.request.TopicPostRequest;
import com.hslzk.share.response.PostResponse;

/**
 * <p>
 * 话题贴子表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface TopicPostService extends IService<TopicPost> {

    IPage<PostResponse> listPostByTopicName(TopicPostRequest topicPostRequest);

}
