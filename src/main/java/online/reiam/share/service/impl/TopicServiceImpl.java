package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.Topic;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.TopicMapper;
import online.reiam.share.request.TopicFollowRequest;
import online.reiam.share.response.TopicResponse;
import online.reiam.share.service.TopicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 话题表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {
    @Resource
    private TopicMapper topicMapper;

    @Override
    public Topic exist(String topicName) {
        Topic topic = getOne(new QueryWrapper<Topic>().lambda().eq(Topic::getName, topicName));
        if (topic == null) {
            throw new MicroShareException(10034, "话题不存在。");
        }
        return topic;
    }

    @Override
    public IPage<TopicResponse> listTopicByUserFollow(TopicFollowRequest topicFollowRequest, Integer userId) {
        Page<TopicResponse> page = new Page<>(topicFollowRequest.getPageNum(), topicFollowRequest.getPageSize());
        return topicMapper.selectTopicListByUserId(page, userId);
    }

}
