package online.reiam.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.TopicPost;
import online.reiam.share.request.TopicPostRequest;
import online.reiam.share.response.PostResponse;

/**
 * <p>
 * 贴子话题表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface TopicPostService extends IService<TopicPost> {

    IPage<PostResponse> listPostByTopicName(TopicPostRequest topicPostRequest);

}
