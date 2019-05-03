package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.Topic;
import online.reiam.share.entity.TopicFollow;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.TopicFollowMapper;
import online.reiam.share.request.TopicFollowRequest;
import online.reiam.share.service.TopicFollowService;
import online.reiam.share.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 话题关注表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class TopicFollowServiceImpl extends ServiceImpl<TopicFollowMapper, TopicFollow> implements TopicFollowService {
    @Autowired
    private TopicService topicService;

    @Override
    public void follow(Integer userId, TopicFollowRequest topicFollowRequest) {
        Topic topic = topicService.exist(topicFollowRequest.getName());
        QueryWrapper<TopicFollow> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TopicFollow::getUserId, userId)
                .eq(TopicFollow::getTopicId, topic.getId());
        TopicFollow topicFollow = getOne(queryWrapper);
        if (topicFollowRequest.getFollow()) {
            TopicFollow topicFollow2 = new TopicFollow();
            if (topicFollow == null) {
                topicFollow2.setUserId(userId)
                        .setTopicId(topic.getId())
                        .setFollow(true)
                        .setDeleted(false)
                        .setVersion(0)
                        .setCreateTime(LocalDateTime.now())
                        .setModifiedTime(LocalDateTime.now());
                save(topicFollow2);
            } else {
                if (!topicFollow.getFollow()) {
                    topicFollow2.setId(topicFollow.getId())
                            .setFollow(true)
                            .setVersion(topicFollow.getVersion())
                            .setModifiedTime(LocalDateTime.now());
                    updateById(topicFollow2);
                } else {
                    throw new MicroShareException(10016, "已关注，请勿重复关注。");
                }
            }
            Topic topic2 = new Topic();
            topic2.setId(topic.getId())
                    .setFollowNum(topic.getFollowNum() + 1)
                    .setVersion(topic.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            topicService.updateById(topic2);
        } else {
            if (topicFollow == null) {
                throw new MicroShareException(10017, "没有关注过，何来取消关注呢？");
            } else {
                if (topicFollow.getFollow()) {
                    TopicFollow topicFollow2 = new TopicFollow();
                    topicFollow2.setId(topicFollow.getId())
                            .setFollow(false)
                            .setVersion(topicFollow.getVersion())
                            .setModifiedTime(LocalDateTime.now());
                    updateById(topicFollow2);
                } else {
                    throw new MicroShareException(10018, "已取消关注，请勿重复取消。");
                }
            }
            Topic topic2 = new Topic();
            topic2.setId(topic.getId())
                    .setFollowNum(topic.getFollowNum() - 1)
                    .setVersion(topic.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            topicService.updateById(topic2);
        }
    }

}
