package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.Topic;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.TopicMapper;
import online.reiam.share.service.TopicService;
import org.springframework.stereotype.Service;

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

    @Override
    public Topic exist(String topicName) {
        Topic topic = getOne(new QueryWrapper<Topic>().lambda().eq(Topic::getName, topicName));
        if (topic == null) {
            throw new MicroShareException(10034, "话题不存在。");
        }
        return topic;
    }

}
