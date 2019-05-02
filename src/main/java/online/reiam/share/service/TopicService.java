package online.reiam.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.Topic;

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

}
