package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.Topic;
import online.reiam.share.entity.TopicPost;
import online.reiam.share.mapper.PostMapper;
import online.reiam.share.mapper.TopicPostMapper;
import online.reiam.share.request.TopicPostRequest;
import online.reiam.share.response.PostResponse;
import online.reiam.share.service.TopicPostService;
import online.reiam.share.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 贴子话题表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class TopicPostServiceImpl extends ServiceImpl<TopicPostMapper, TopicPost> implements TopicPostService {
    @Autowired
    private TopicService topicService;
    @Resource
    private TopicPostMapper topicPostMapper;
    @Resource
    private PostMapper postService;

    @Override
    public IPage<PostResponse> listPostByTopicName(TopicPostRequest topicPostRequest) {
        Topic topic = topicService.exist(topicPostRequest.getName());
        Page<Integer> page = new Page<>(topicPostRequest.getPageNum(), topicPostRequest.getPageSize());
        IPage<Integer> page2 = topicPostMapper.selectPostIdListByTopicId(page, topic.getId());
        IPage<PostResponse> page3 = new Page<>(topicPostRequest.getPageNum(), topicPostRequest.getPageSize());
        page3.setRecords(postService.selectPostListByIdList(page2.getRecords()))
                .setTotal(page2.getTotal())
                .setSize(page2.getSize())
                .setCurrent(page2.getCurrent())
                .setPages(page2.getPages());
        return page3;
    }

}
