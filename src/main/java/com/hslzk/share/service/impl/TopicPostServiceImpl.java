package com.hslzk.share.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hslzk.share.response.PostResponse;
import com.hslzk.share.service.TopicPostService;
import com.hslzk.share.entity.Topic;
import com.hslzk.share.entity.TopicPost;
import com.hslzk.share.mapper.PostMapper;
import com.hslzk.share.mapper.TopicPostMapper;
import com.hslzk.share.request.TopicPostRequest;
import com.hslzk.share.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 话题贴子表 服务实现类
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
    private PostMapper postMapper;

    @Override
    public IPage<PostResponse> listPostByTopicName(TopicPostRequest topicPostRequest) {
        Topic topic = topicService.exist(topicPostRequest.getName());
        Page<Integer> page = new Page<>(topicPostRequest.getPageNum(), topicPostRequest.getPageSize());
        IPage<Integer> page2 = topicPostMapper.selectPostIdListByTopicId(page, topic.getId());
        IPage<PostResponse> page3 = new Page<>(topicPostRequest.getPageNum(), topicPostRequest.getPageSize());
        page3.setRecords(postMapper.selectPostListByIdList(page2.getRecords()))
                .setTotal(page2.getTotal()).setPages(page2.getPages()).setSize(page2.getSize()).setCurrent(page2.getCurrent());
        return page3;
    }

}
