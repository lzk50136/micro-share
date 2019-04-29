package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.entity.*;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.mapper.PostAtCustomMapper;
import online.reiam.share.mapper.PostCustomMapper;
import online.reiam.share.mapper.PostLikesCustomMapper;
import online.reiam.share.mapper.UserFollowCustomMapper;
import online.reiam.share.request.PostRequest;
import online.reiam.share.response.LikesPostResponse;
import online.reiam.share.response.PostAtResponse;
import online.reiam.share.response.PostResponse;
import online.reiam.share.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostCustomServiceImpl implements PostCustomService {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostDetailService postDetailService;
    @Resource
    private PostCustomMapper postCustomMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PostAtService postAtService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicPostService topicPostService;
    @Resource
    private UserFollowCustomMapper userFollowCustomMapper;
    @Resource
    private PostAtCustomMapper postAtCustomMapper;
    @Resource
    private PostLikesCustomMapper postLikesCustomMapper;

    /**
     * 艾特正则表达式
     */
    private Pattern pattern = Pattern.compile("@(?<name>[a-zA-Z\\d_\\u4e00-\\u9fa5]{1,14})\\s+");
    /**
     * 话题正则表达式
     */
    private Pattern pattern2 = Pattern.compile("#(?<name>[a-zA-Z\\d_\\u4e00-\\u9fa5]{1,14})\\s+");

    /**
     * 发布新贴子
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostResponse addPost(Integer userId, PostRequest postRequest) {
        // 检查图片是否存在以及排序是否重复
        Map<Integer, Integer> integerMap = new HashMap<>();
        for (int i = 0; i < postRequest.getPostDetailRequestList().size(); i++) {
            Integer sort = postRequest.getPostDetailRequestList().get(i).getSort();
            if (!integerMap.containsKey(sort)) {
                integerMap.put(sort, sort);
            } else {
                throw new MicroShareException(10022, "排序值有重复。");
            }
            Integer resourceId = postRequest.getPostDetailRequestList().get(i).getResourceId();
            online.reiam.share.entity.Resource resource = resourceService.getById(resourceId);
            if (resource == null) {
                throw new MicroShareException(10023, "部分资源不存在。");
            }
        }
        // 插入用户贴子表
        Post post = new Post();
        post.setUserId(userId)
                .setTitle(postRequest.getTitle())
                .setLikesNum(0)
                .setCommentNum(0)
                .setAllowComment(postRequest.getAllowComment())
                .setDeleted(false)
                .setVersion(0)
                .setCreateTime(LocalDateTime.now())
                .setModifiedTime(LocalDateTime.now());
        postService.save(post);
        // 插入贴子详情表
        List<PostDetail> postDetailList = new ArrayList<>();
        for (int i = 0; i < postRequest.getPostDetailRequestList().size(); i++) {
            PostDetail postDetail = new PostDetail();
            postDetail.setPostId(post.getId())
                    .setSort(postRequest.getPostDetailRequestList().get(i).getSort())
                    .setResourceId(postRequest.getPostDetailRequestList().get(i).getResourceId())
                    .setDeleted(false)
                    .setVersion(0)
                    .setCreateTime(LocalDateTime.now())
                    .setModifiedTime(LocalDateTime.now());
            postDetailList.add(postDetail);
        }
        postDetailService.saveBatch(postDetailList);
        // 如果正文有@某个用户，则插入通知记录
        Matcher matcher = pattern.matcher(postRequest.getTitle());
        while (matcher.find()) {
            String name = matcher.group("name");
            // 判断用户是否存在
            UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getNickname, name));
            if (userInfo != null) {
                PostAt postAt = new PostAt();
                postAt.setPostId(post.getId())
                        .setUserId(userInfo.getUserId())
                        .setDeleted(false)
                        .setVersion(0)
                        .setCreateTime(LocalDateTime.now())
                        .setModifiedTime(LocalDateTime.now());
                postAtService.save(postAt);
            }
        }
        // 如果正文有#某个话题，则更新话题贴子数量
        Matcher matcher2 = pattern2.matcher(postRequest.getTitle());
        while (matcher2.find()) {
            String name = matcher2.group("name");
            // 判断话题是否存在
            Topic topic = topicService.getOne(new QueryWrapper<Topic>().lambda().eq(Topic::getName, name));
            if (topic == null) {
                // 若话题不存在，则自动创建新的话题
                topic = new Topic();
                topic.setName(name)
                        .setFollowNum(0)
                        .setPostNum(0)
                        .setDeleted(false)
                        .setVersion(0)
                        .setCreateTime(LocalDateTime.now())
                        .setModifiedTime(LocalDateTime.now());
                topicService.save(topic);
            }
            // 插入贴子话题表
            TopicPost topicPost = new TopicPost();
            topicPost.setTopicId(topic.getId())
                    .setPostId(post.getId())
                    .setDeleted(false)
                    .setVersion(0)
                    .setCreateTime(LocalDateTime.now())
                    .setModifiedTime(LocalDateTime.now());
            topicPostService.save(topicPost);
            // 更新话题相关贴子数
            Topic topic2 = new Topic();
            topic2.setId(topic.getId())
                    .setPostNum(topic.getPostNum() + 1)
                    .setVersion(topic.getVersion())
                    .setModifiedTime(LocalDateTime.now());
            topicService.updateById(topic2);
        }
        return postCustomMapper.selectPostById(post.getId());
    }

    /**
     * 删除贴子及贴子详情
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removePostById(Integer userId, Integer postId) {
        Post post = postService.getById(postId);
        if (post == null) {
            throw new MicroShareException(10024, "贴子不存在。");
        }
        if (!post.getUserId().equals(userId)) {
            throw new MicroShareException(10025, "不属于你的贴子。");
        }
        postService.removeById(postId);
        postDetailService.remove(new QueryWrapper<PostDetail>().lambda().eq(PostDetail::getPostId, postId));
    }

    /**
     * 获取某个用户的贴子列表
     */
    @Override
    public IPage<PostResponse> listPostByUserId(PostRequest postRequest, UserInfo userInfo) {
        Page<PostResponse> page = new Page<>(postRequest.getPageNum(), postRequest.getPageSize());
        return postCustomMapper.selectPostListByUserId(page, userInfo.getUserId());
    }

    /**
     * 获取某个话题的贴子列表
     */
    @Override
    public IPage<PostResponse> listPostByTopicId(PostRequest postRequest) {
        Topic topic = topicService.getOne(new QueryWrapper<Topic>().lambda().eq(Topic::getName, postRequest.getTopicName()));
        if (topic == null) {
            throw new MicroShareException(10026, "话题不存在。");
        }
        Page<PostResponse> page = new Page<>(postRequest.getPageNum(), postRequest.getPageSize());
        return postCustomMapper.selectPostListByTopicId(page, topic.getId());
    }

    /**
     * 刷新关注的用户的贴子列表
     */
    @Override
    public IPage<PostResponse> listPostByFollowId(Integer userId, PostRequest postRequest) {
        // 需要获取全部关注列表
        List<Integer> integerList = userFollowCustomMapper.selectFollowIdListByUserId(userId);
        // 关注列表的新贴子需要分页
        Page<PostResponse> page = new Page<>(postRequest.getPageNum(), postRequest.getPageSize());
        return postCustomMapper.selectPostListByFollowId(page, integerList, postRequest.getStartTime(), postRequest.getEndTime());
    }

    /**
     * 获取艾特我的贴子列表
     */
    @Override
    public IPage<PostAtResponse> listPostByAtMe(PostRequest postRequest, Integer userId) {
        Page<PostAtResponse> page = new Page<>(postRequest.getPageNum(), postRequest.getPageSize());
        return postAtCustomMapper.selectPostListByUserId(page, userId);
    }

    /**
     * 获取点赞我的贴子列表
     */
    @Override
    public IPage<LikesPostResponse> listPostByLikeMe(PostRequest postRequest, Integer userId) {
        Page<LikesPostResponse> page = new Page<>(postRequest.getPageNum(), postRequest.getPageSize());
        return postLikesCustomMapper.selectLikesPostListByUserId(page, userId);
    }

}
